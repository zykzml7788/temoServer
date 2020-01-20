package com.creams.temo.controller.task;

import com.creams.temo.entity.JsonResult;
import com.creams.temo.entity.task.request.TaskRequest;
import com.creams.temo.entity.task.response.TaskResponse;
import com.creams.temo.service.task.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import java.util.List;

/**
 * 任务
 *
 */
@RestController
@Api("TaskController APi")
@RequestMapping("/task")
public class TaskController {

    @Autowired
    public TaskService taskService;

    @ApiOperation(value = "新增任务")
    @PostMapping("")
    public JsonResult addTask(@RequestBody TaskRequest task){
        try{
            taskService.addTask(task);
            return new JsonResult("操作成功", 200, null, true);
        }catch (Exception e){
            return new JsonResult("操作失败", 500, e, false);
        }

    }

    @ApiOperation(value = "根据任务名和执行状态查询任务")
    @GetMapping("")
    public JsonResult queryTasks(@RequestParam(value = "taskName",required = false) String taskName,@RequestParam(value = "status",required = false) Integer status){
        List<TaskResponse> taskResponses = taskService.queryTasks(taskName,status);
        return new JsonResult("操作成功", 200, taskResponses, true);
    }


    @ApiOperation(value = "查询任务详情")
    @GetMapping("/{taskId}")
    public JsonResult queryTaskDetail(@PathVariable("taskId") String taskId){
        TaskResponse taskResponse = taskService.queryTaskDetail(taskId);
        return new JsonResult("操作成功", 200, taskResponse, true);
    }


    @ApiOperation(value = "编辑任务")
    @PutMapping("/{taskId}")
    public JsonResult updateTask(@PathVariable("taskId") String taskId,@RequestBody TaskRequest taskRequest){
        try {
            taskService.updateTask(taskRequest);
            return new JsonResult("操作成功", 200, null, true);
        }catch (Exception e){
            return new JsonResult("操作失败", 500, e, false);
        }
    }


    @ApiOperation(value = "删除任务")
    @DeleteMapping("/{taskId}")
    public JsonResult deleteTask(@PathVariable("taskId") String taskId){
        try {
            taskService.deleteTask(taskId);
            return new JsonResult("操作成功", 200, null, true);
        }catch (Exception e){
            return new JsonResult("操作失败", 500, e, false);
        }

    }


    @ApiOperation(value = "发起任务")
    @PostMapping("/start/{taskId}")
    public JsonResult startTask(@PathVariable("taskId") String taskId){
        TaskResponse taskResponse = taskService.queryTaskDetail(taskId);
        String isParallel = taskResponse.getIsParallel();
        if ("0".equals(isParallel)){
            taskService.startSynchronizeTask(taskId);
            return new JsonResult("已成功发起同步任务", 200, null, true);
        }else {
            taskService.startAsnchronizeTask(taskId);
            return new JsonResult("已成功发起异步任务", 200, null, true);
        }
    }

}