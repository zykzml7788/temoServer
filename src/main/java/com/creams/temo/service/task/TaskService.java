package com.creams.temo.service.task;

import com.alibaba.fastjson.JSON;
import com.creams.temo.entity.task.TestSet;
import com.creams.temo.entity.task.request.TaskRequest;
import com.creams.temo.entity.task.response.TaskResponse;
import com.creams.temo.mapper.task.TaskMapper;
import com.creams.temo.service.testcase.TestCaseSetService;
import com.creams.temo.util.StringUtil;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * 新增任务
     * @param taskRequest
     */
    public void addTask(TaskRequest taskRequest){
        taskRequest.setTaskId(StringUtil.uuid());
        taskMapper.addTask(taskRequest);
    }

    /**
     * 编辑任务
     * @param taskRequest
     */
    public void updateTask(TaskRequest taskRequest){
        taskMapper.updateTaskById(taskRequest);
    }

    /**
     * 删除任务
     * @param id
     */
    public void deleteTask(String id){
        taskMapper.deleteTask(id);
    }

    /**
     * 根据任务名和状态筛选任务
     */
    public List<TaskResponse> queryTasks(String taskName,Integer status){
        return taskMapper.queryTasks(taskName,status);
    }

    /**
     * 查询任务详情
     * @param taskId
     */
    public TaskResponse queryTaskDetail(String taskId){
        return taskMapper.queryTaskDetail(taskId);
    }

    /**
     * 发起任务
     * @param taskId
     */
    public void startTask(String taskId){
        // 获取任务相关联的需要执行的用例集
        TaskResponse taskResponse = queryTaskDetail(taskId);
        List testSets = JSON.parseObject(taskResponse.getTestSets(),List.class);
        if ("0".equals(taskResponse.getIsTiming())){
            // 不定时执行
            for (Object testSet:testSets){
                TestSet set = (TestSet)testSet;
                String setId = set.getSetId();
                String envId = set.getEnvId();
                try {
                    testCaseSetService.executeSet(setId,envId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else {
            //定义一个Trigger
            Trigger trigger = newTrigger().withIdentity(taskResponse.getTaskName())
                    .startNow()//一旦加入scheduler，立即生效
                    .withSchedule(cronSchedule(taskResponse.getCron()))
                    .build();
            //定义一个JobDetail
            JobDetail job = newJob(TaskScheduler.class)
                    .withIdentity(taskResponse.getTaskName())
                    .usingJobData("testSets", taskResponse.getTestSets())
                    .build();
            // 把job加入到任务调度器
            taskScheduler.addJob(job,trigger);

        }
    }

}

