package com.creams.temo.controller.datastatistics;

import com.creams.temo.entity.JsonResult;

import com.creams.temo.entity.datastatistics.response.*;
import com.creams.temo.service.datastatistics.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("DataStatisticsController Api")
@RestController
@RequestMapping("/dataStatistics")
public class DataStatisticsController {

    @Autowired
    ExecuteTodayService executeTodayService;

    @Autowired
    ExecuteTimeInfoService executeTimeInfoService;

    @Autowired
    TasksInfoService tasksInfoService;

    @Autowired
    TestCaseSetInfoService testCaseSetInfoService;

    @Autowired
    SevenDaysExecuteInfoService sevenDaysExecuteInfoService;

    @ApiOperation(value = "查询任务库信息")
    @GetMapping("/taskInfo")
    public JsonResult queryTasksInfo() {
        try {
            TaskInfoResponse taskInfoResponse = tasksInfoService.queryTasksInfo();
            return new JsonResult("获取成功", 200, taskInfoResponse, true);
        }catch (Exception e){
            return new JsonResult("获取失败", 500, null, false);
        }
    }

    @ApiOperation(value = "查询任务执行信息")
    @GetMapping("/taskExecuteInfo")
    public JsonResult queryTasksExecuteInfo() {
        try {
            TaskInfoResponse taskInfoResponse = tasksInfoService.queryTasksExecuteInfo();
            return new JsonResult("获取成功", 200, taskInfoResponse, true);
        }catch (Exception e){
            return new JsonResult("获取失败", 500, null, false);
        }
    }


    @ApiOperation(value = "查询用例库信息")
    @GetMapping("/setInfo")
    public JsonResult queryTestCaseSetInfo() {
        try {
            TesSetInfoResponse tesSetInfoResponse = testCaseSetInfoService.queryTestSetInfo();
            return new JsonResult("获取成功", 200, tesSetInfoResponse, true);
        }catch (Exception e){
            return new JsonResult("获取失败", 500, null, false);
        }

    }

    @ApiOperation(value = "查询今日执行情况")
    @GetMapping("/todayInfo")
    public JsonResult queryTodayExecuteTaskInfo() {
        try {
            ExecuteTodayResponse executeTodayResponse = executeTodayService.queryTodayExecuteTaskInfo();
            if (executeTodayResponse!=null){
                return new JsonResult("获取成功", 200, executeTodayResponse, true);
            }else{
                executeTodayResponse = new ExecuteTodayResponse();
                executeTodayResponse.setExecuteCaseTodayNum(0);
                executeTodayResponse.setFalseNum("0");
                executeTodayResponse.setSuccessNum("0");
                executeTodayResponse.setSuccessRate("0.00");
                return new JsonResult("获取成功", 200, executeTodayResponse, true);
            }
        }catch (Exception e){
            return new JsonResult("获取失败", 500, null, false);
        }

    }

    @ApiOperation(value = "查询今日用例执行情况")
    @GetMapping("/todayCaseInfo")
    public JsonResult queryTodayExecuteTestCaseInfo() {
        try {
            ExecuteTodayResponse executeTodayResponse = executeTodayService.queryTodayExecuteTestCaseInfo();
            if (executeTodayResponse!=null){
                return new JsonResult("获取成功", 200, executeTodayResponse, true);
            }else {
                executeTodayResponse = new ExecuteTodayResponse();
                executeTodayResponse.setExecuteCaseTodayNum(0);
                executeTodayResponse.setFalseNum("0");
                executeTodayResponse.setSuccessNum("0");
                executeTodayResponse.setSuccessRate("0.00");
                return new JsonResult("获取成功", 200, executeTodayResponse, true);
            }

        }catch (Exception e){
            return new JsonResult("获取失败", 500, null, false);
        }

    }


    @ApiOperation(value = "查询运行信息")
    @GetMapping("/executeInfoNow")
    public JsonResult queryExecuteTaskInfoNow() {
        try {
            ExecuteTimeInfoResponse executeInfoResponse = executeTimeInfoService.queryExecuteTimeInfo();
            return new JsonResult("获取成功", 200, executeInfoResponse, true);
        }catch (Exception e){
            return new JsonResult("获取失败", 500, null, false);
        }
    }

    @ApiOperation(value = "查询七日用例执行情况")
    @GetMapping("/SevenDaysInfo")
    public JsonResult querySevenDaysExecuteInfo() {
        try {
            ExecuteSevenDaysResponse executeSevenDaysResponse = sevenDaysExecuteInfoService.querySevenDaysExecuteInfo();
            return new JsonResult("获取成功", 200, executeSevenDaysResponse, true);
        }catch (Exception e){
            return new JsonResult("获取失败", 500, null, false);
        }
    }
}
