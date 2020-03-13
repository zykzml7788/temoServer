package com.creams.temo.service.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.creams.temo.entity.database.response.ScriptDbResponse;
import com.creams.temo.entity.task.SetResult;
import com.creams.temo.entity.task.TaskResult;
import com.creams.temo.entity.task.TestSet;
import com.creams.temo.entity.task.request.TaskRequest;
import com.creams.temo.entity.task.request.TimingTaskRequest;
import com.creams.temo.entity.task.response.TaskResponse;
import com.creams.temo.entity.task.response.TimingTaskResponse;
import com.creams.temo.entity.testcase.response.TestCaseResponse;
import com.creams.temo.entity.testcase.response.TestCaseSetResponse;
import com.creams.temo.mapper.task.SetResultMapper;
import com.creams.temo.mapper.task.TaskMapper;
import com.creams.temo.mapper.task.TaskResultMapper;
import com.creams.temo.service.testcase.TestCaseSetService;
import com.creams.temo.util.DateUtil;
import com.creams.temo.util.StringUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jdk.nashorn.internal.runtime.Timing;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    public SetResultMapper setResultMapper;

    /**
     * 新增任务
     *
     * @param taskRequest
     */
    public void addTask(TaskRequest taskRequest) {
        taskRequest.setTaskId(StringUtil.uuid());
        taskMapper.addTask(taskRequest);
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
    @Transactional
    public void deleteTask(String id) {
        taskMapper.deleteTask(id);
    }

    /**
     * 根据任务名和状态筛选任务
     */
    public PageInfo<TaskResponse> queryTasks(Integer page,String taskName, String isParallel) {
        PageHelper.startPage(page, 10);
        List<TaskResponse> taskResponses = taskMapper.queryTasks(taskName, isParallel);
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
     * 根据定时任务名和状态筛选定时任务
     */
    public PageInfo<TimingTaskResponse> queryTimingTasks(Integer page,String taskName, String isParallel) {
        PageHelper.startPage(page, 10);
        List<TimingTaskResponse> timingTaskResponses = taskMapper.queryTimingTasks(taskName, isParallel);
        for (TimingTaskResponse timingTaskResponse : timingTaskResponses){
            List<TestSet> testSets = JSON.parseArray(timingTaskResponse.getTestSets().replaceAll("\\\\", ""), TestSet.class);
            for (TestSet testSet:testSets){
                testSet.setSetName(testCaseSetService.queryTestCaseSetInfo(testSet.getSetId()).getSetName());
            }
            timingTaskResponse.setTestSetList(testSets);
        }
        return new PageInfo<>(timingTaskResponses);
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
     * 查询定时任务详情
     *
     * @param taskId
     */
    public TimingTaskResponse queryTimingTaskDetail(String taskId) {
        TimingTaskResponse timingTaskResponse = taskMapper.queryTimingTaskDetail(taskId);
        List<TestSet> testSets = JSON.parseArray(timingTaskResponse.getTestSets().replaceAll("\\\\", ""), TestSet.class);
        for (TestSet testSet:testSets){
            testSet.setSetName(testCaseSetService.queryTestCaseSetInfo(testSet.getSetId()).getSetName());
        }
        timingTaskResponse.setTestSetList(testSets);
        return timingTaskResponse;
    }

    @Transactional
    public boolean addTimingTask(TimingTaskRequest timingTaskRequest){
        timingTaskRequest.setTaskId(StringUtil.uuid());
        return taskMapper.addTimingTask(timingTaskRequest);
    }

    /**
     * 编辑定时任务
     *
     * @param timingTaskRequest
     */
    public void updateTimingTask(TimingTaskRequest timingTaskRequest) {
        taskMapper.updateTimingTask(timingTaskRequest);
    }
    /**
     * 发起串行任务
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
        taskResult.setTaskResultNum("task-"+System.currentTimeMillis());
        taskResult.setTaskName(taskResponse.getTaskName());
        taskResult.setStatus(1);
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
        taskResult.setStatus(2);
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
        taskResult.setTaskResultNum("task-"+System.currentTimeMillis());
        taskResult.setTaskName(taskResponse.getTaskName());
        taskResult.setStatus(1);
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
                }
            }
            if (isAllDone) {
                break;
            }
        }
        for (Future<Boolean> result : results) {
            if (result.get()) {
                successNum++;
            }
        }
        // 更新执行记录状态
        taskResult.setEndTime(DateUtil.getCurrentTimestamp());
        taskResult.setStatus(2);
        taskResult.setSuccessNum(successNum);
        taskResult.setTotalNum(total);
        // 计算成功率
        DecimalFormat df = new DecimalFormat("0.00");
        String successRate = df.format(((float)successNum/total)*100);
        taskResult.setSuccessRate(successRate);
        taskResultMapper.updateTaskResult(taskResult);


    }

    /**
     * 开启定时任务
     *
     * @param taskId
     */
    @Transactional
    public void startTimingTask(String taskId) {
        // 获取任务相关联的需要执行的用例集
        TimingTaskResponse timingTaskResponse = queryTimingTaskDetail(taskId);
        // 更新状态为开启
        taskMapper.updateTimingTaskStatus(taskId,1);
        // 定时执行

        //定义一个Trigger
        Trigger trigger = newTrigger().withIdentity(timingTaskResponse.getTaskId())
                .startNow()//一旦加入scheduler，立即生效
                .withSchedule(cronSchedule(timingTaskResponse.getCron()))
                .build();
        //定义一个JobDetail
        JobDetail job = newJob(TaskScheduler.class)
                .withIdentity(timingTaskResponse.getTaskId())
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
    @Transactional
    public void closeTimingTask(String taskId){
        // 更新状态为开启
        taskMapper.updateTimingTaskStatus(taskId,0);
        taskScheduler.deleteJob(taskId);
    }

    /**
     * 查询执行任务结果
     * @param page
     * @param taskName
     * @param status
     * @return
     */
    public PageInfo<TaskResult> queryTaskResults(Integer page,String taskName,Integer status){
        PageHelper.startPage(page, 10);
        List<TaskResult> taskResults = taskResultMapper.queryTaskResults(taskName,status);
        return new PageInfo<>(taskResults);
    }

    /**
     * 查询任务执行接结果详情
     * @param taskResultId
     * @return
     */
    public List<SetResult> queryTaskResultDetail(String taskResultId){
        return setResultMapper.querySetResultsByTaskResultId(taskResultId);
    }
}

