package com.creams.temo.service.testcase;

import com.creams.temo.service.TaskScheduler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCaseSetServiceTest {

    @Autowired
    TestCaseSetService testCaseSetService;

    @Autowired
    TaskScheduler taskScheduler;

    @Test
    public void testQuartz(){
        //定义一个Trigger
        Trigger trigger = newTrigger().withIdentity("trigger1", "group1") //定义name/group
                .startNow()//一旦加入scheduler，立即生效
                .withSchedule(cronSchedule("0 0 16 * * ?")) //一直执行，奔腾到老不停歇
                .build();

        //定义一个JobDetail
        JobDetail job = newJob(HelloQuartz.class) //定义Job类为HelloQuartz类，这是真正的执行逻辑所在
                .withIdentity("job1", "group1") //定义name/group
                .usingJobData("name", "quartz")//定义属性
                .build();


        //定义一个Trigger
        Trigger trigger2 = newTrigger().withIdentity("trigger2", "group2") //定义name/group
                .startNow()//一旦加入scheduler，立即生效
                .withSchedule(simpleSchedule() //使用SimpleTrigger
                        .withIntervalInSeconds(1) //每隔一秒执行一次
                        .repeatForever()) //一直执行，奔腾到老不停歇
                .build();

        //定义一个JobDetail
        JobDetail job2 = newJob(HelloQuartz.class) //定义Job类为HelloQuartz类，这是真正的执行逻辑所在
                .withIdentity("job2", "group2") //定义name/group
                .usingJobData("name", "quartz") //定义属性
                .build();
        taskScheduler.addJob(job,trigger);
        taskScheduler.addJob(job2,trigger2);
    }

}