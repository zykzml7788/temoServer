package com.creams.temo.service.task;

import com.alibaba.fastjson.JSON;
import com.creams.temo.entity.task.TestSet;
import com.creams.temo.entity.task.response.TaskResponse;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 定时任务调度器
 */
@Component
public class TaskScheduler implements Job {

    private static Logger logger = LoggerFactory.getLogger("fileInfoLog");
    public static TaskScheduler taskScheduler;

    Scheduler scheduler;


    @Autowired
    @Lazy
    public TaskService taskService;


    @PostConstruct
    public void init(){
        taskScheduler = this;
        taskScheduler.taskService = this.taskService;
    }

    public TaskScheduler() throws SchedulerException {
        //创建scheduler
        scheduler = StdSchedulerFactory.getDefaultScheduler();
        //启动之
        scheduler.start();
    }

    /**
     * 向调度器添加定时任务
     * @param job  任务
     * @param trigger  执行策略
     */
    public void addJob(JobDetail job,Trigger trigger){
        try {
            scheduler.scheduleJob(job,trigger);
        } catch (SchedulerException e) {
            logger.error("添加定时任务失败！Exception:"+e);
            e.printStackTrace();
        }
    }

    /**
     * 从调度器中删除定时任务
     * @param jobKey  JobKey
     */
    public void deleteJob(String jobKey){
        try {
            scheduler.deleteJob(new JobKey(jobKey));
        } catch (SchedulerException e) {
            logger.error("删除定时任务失败！Exception:"+e);
            e.printStackTrace();
        }
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        //   执行逻辑，此处应写执行任务的逻辑
        String taskId = jobExecutionContext.getJobDetail().getJobDataMap().get("taskId").toString();
        TaskResponse taskResponse = taskScheduler.taskService.queryTaskDetail(taskId);
        String isParallel = taskResponse.getIsParallel();
        // 判断是并发还是同步执行
        if ("0".equals(isParallel)){
            taskScheduler.taskService.startSynchronizeTask(taskId);
        }else {
            taskScheduler.taskService.startAsnchronizeTask(taskId);
        }
    }
}
