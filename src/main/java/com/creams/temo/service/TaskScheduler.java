package com.creams.temo.service;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 定时任务调度器
 */
@Component
public class TaskScheduler implements Job {

    private static Logger logger = LoggerFactory.getLogger("fileInfoLog");

    Scheduler scheduler;


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
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // 执行逻辑，此处应写执行任务的逻辑
    }
}
