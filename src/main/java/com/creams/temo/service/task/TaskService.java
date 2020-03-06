package com.creams.temo.service.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.creams.temo.entity.database.response.ScriptDbResponse;
import com.creams.temo.entity.task.TaskResult;
import com.creams.temo.entity.task.TestSet;
import com.creams.temo.entity.task.request.TaskRequest;
import com.creams.temo.entity.task.response.TaskResponse;
import com.creams.temo.entity.testcase.response.TestCaseResponse;
import com.creams.temo.entity.testcase.response.TestCaseSetResponse;
import com.creams.temo.mapper.task.TaskMapper;
import com.creams.temo.mapper.task.TaskResultMapper;
import com.creams.temo.service.testcase.TestCaseSetService;
import com.creams.temo.util.DateUtil;
import com.creams.temo.util.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * 任务service
 */

@Service
public class TaskService {


    @Autowired
    public TaskMapper taskMapper;


    @Autowired
    public TaskScheduler taskScheduler;

    @Autowired
    public TestCaseSetService testCaseSetService;

    @Autowired
    public TaskResultMapper taskResultMapper;

    /**
     * 新增任务
     *
     * @param taskRequest
     */
    public void addTask(TaskRequest taskRequest) {
        taskRequest.setTaskId(StringUtil.uuid());
        taskMapper.addTask(taskRequest);
        // 激活定时任务
        if ("1".equals(taskRequest.getIsTiming())){
            // 发起定时任务
            //定义一个Trigger
            Trigger trigger = newTrigger().withIdentity(taskRequest.getTaskName())
                    .startNow()//一旦加入scheduler，立即生效
                    .withSchedule(cronSchedule(taskRequest.getCron()))
                    .build();
            //定义一个JobDetail
            JobDetail job = newJob(TaskScheduler.class)
                    .withIdentity(taskRequest.getTaskName())
                    .usingJobData("taskId",taskRequest.getTaskId())
                    .build();
            // 把job加入到任务调度器
            taskScheduler.addJob(job, trigger);
        }
    }

    /**
     * 编辑任务
     *
     * @param taskRequest
     */
    public void updateTask(TaskRequest taskRequest) {
        taskMapper.updateTaskById(taskRequest);
    }

    /**
     * 删除任务
     *
     * @param id
     */
    public void deleteTask(String id) {
        TaskResponse taskResponse = taskMapper.queryTaskDetail(id);
        // 如果是定时任务，则从调度器中移除
        if ("1".equals(taskResponse.getIsTiming())){
            taskScheduler.deleteJob(id);
        }
        taskMapper.deleteTask(id);
    }

    /**
     * 根据任务名和状态筛选任务
     */
    public PageInfo<TaskResponse> queryTasks(Integer page,String taskName, Integer status) {
        PageHelper.startPage(page, 10);
        List<TaskResponse> taskResponses = taskMapper.queryTasks(taskName, status);
        for (TaskResponse taskResponse : taskResponses){
            List<TestSet> testSets = JSON.parseArray(taskResponse.getTestSets().replaceAll("\\\\", ""), TestSet.class);
            for (TestSet testSet:testSets){
                testSet.setSetName(testCaseSetService.queryTestCaseSetInfo(testSet.getSetId()).getSetName());
            }
            taskResponse.setTestSetList(testSets);
        }
        return new PageInfo<>(taskResponses);
    }

    /**
     * 查询任务详情
     *
     * @param taskId
     */
    public TaskResponse queryTaskDetail(String taskId) {
        TaskResponse taskResponse = taskMapper.queryTaskDetail(taskId);
        List<TestSet> testSets = JSON.parseArray(taskResponse.getTestSets().replaceAll("\\\\", ""), TestSet.class);
        for (TestSet testSet:testSets){
            testSet.setSetName(testCaseSetService.queryTestCaseSetInfo(testSet.getSetId()).getSetName());
        }
        taskResponse.setTestSetList(testSets);
        return taskResponse;
    }

    /**
     * 发起同步任务
     *
     * @param taskId
     */
    @Async
    public void startSynchronizeTask(String taskId) {
        // 获取任务相关联的需要执行的用例集
        TaskResponse taskResponse = queryTaskDetail(taskId);
        // 添加执行记录
        String taskResultId = StringUtil.uuid();
        TaskResult taskResult = new TaskResult();
        taskResult.setTaskName(taskResponse.getTaskName());
        taskResult.setStatus(0);
        taskResult.setStartTime(DateUtil.getCurrentTimestamp());
        taskResult.setPerson("系统用户");
        taskResult.setTaskId(taskId);
        taskResult.setTaskResultId(taskResultId);
        taskResultMapper.addTaskResult(taskResult);
        // 转换json
        List<TestSet> testSets = JSON.parseArray(taskResponse.getTestSets().replaceAll("\\\\", ""), TestSet.class);
        // 立即执行
        Integer total = testSets.size();
        Integer successNum = 0;
        for (TestSet testSet : testSets) {
            try {
                // 同步调用用例集
                Boolean result = testCaseSetService.executeSetBySynchronizeTask(taskResultId, testSet);
                if (result){
                    successNum++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 更新执行记录状态
        taskResult.setEndTime(DateUtil.getCurrentTimestamp());
        taskResult.setStatus(1);
        taskResult.setSuccessNum(successNum);
        taskResult.setTotalNum(total);
        // 计算成功率
        DecimalFormat df = new DecimalFormat("0.00");
        String successRate = df.format(((float)successNum/total)*100);
        taskResult.setSuccessRate(successRate);
        taskResultMapper.updateTaskResult(taskResult);

    }

    /**
     * 发起并发任务
     *
     * @param taskId
     */
    @Async
    public void startAsnchronizeTask(String taskId) throws ExecutionException, InterruptedException {
        // 获取任务相关联的需要执行的用例集
        TaskResponse taskResponse = queryTaskDetail(taskId);
        // 添加执行记录
        TaskResult taskResult = new TaskResult();
        String taskResultId = StringUtil.uuid();
        taskResult.setTaskName(taskResponse.getTaskName());
        taskResult.setStatus(0);
        taskResult.setStartTime(DateUtil.getCurrentTimestamp());
        taskResult.setPerson("系统用户");
        taskResult.setTaskId(taskId);
        taskResult.setTaskResultId(taskResultId);
        taskResultMapper.addTaskResult(taskResult);
        // 转换json
        List<TestSet> testSets = JSON.parseArray(taskResponse.getTestSets().replaceAll("\\\\", ""), TestSet.class);
        // 所有的执行结果future
        List<Future<Boolean>> results = new ArrayList<>();
        // 立即执行
        Integer total = testSets.size();
        Integer successNum = 0;
        for (TestSet testSet : testSets) {
            try {
                // 异步调用用例集
                Future<Boolean> result = testCaseSetService.executeSetByAsynchronizeTask(taskResultId, testSet);
                results.add(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        while (true) {
            boolean isAllDone = true;
            for (Future<Boolean> result : results) {
                if (!result.isDone()) {
                    // 如果没执行完，标记isAllDone为false
                    isAllDone = false;
                }else{
                    // 如果执行完了
                    if (result.get()){
                        // 获取用例集测试结果，如果为true
                        successNum ++;
                    }
                }
            }
            if (isAllDone) {
                // 更新执行记录状态
                taskResult.setEndTime(DateUtil.getCurrentTimestamp());
                taskResult.setStatus(1);
                taskResult.setSuccessNum(successNum);
                taskResult.setTotalNum(total);
                // 计算成功率
                DecimalFormat df = new DecimalFormat("0.00");
                String successRate = df.format(((float)successNum/total)*100);
                taskResult.setSuccessRate(successRate);
                taskResultMapper.updateTaskResult(taskResult);
                break;
            }
        }


    }

    /**
     * 开启定时任务
     *
     * @param taskId
     */
    public void startTimingTask(String taskId) {
        // 获取任务相关联的需要执行的用例集
        TaskResponse taskResponse = queryTaskDetail(taskId);

        // 定时执行

        //定义一个Trigger
        Trigger trigger = newTrigger().withIdentity(taskResponse.getTaskId())
                .startNow()//一旦加入scheduler，立即生效
                .withSchedule(cronSchedule(taskResponse.getCron()))
                .build();
        //定义一个JobDetail
        JobDetail job = newJob(TaskScheduler.class)
                .withIdentity(taskResponse.getTaskId())
                .usingJobData("taskId",taskId)
                .build();
        // 把job加入到任务调度器
        taskScheduler.addJob(job, trigger);
    }

    /**
     * 关闭定时任务
     *
     * @param taskId
     */
    public void closeTimingTask(String taskId){

        taskScheduler.deleteJob(taskId);
    }

}

