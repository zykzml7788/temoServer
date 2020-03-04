package com.creams.temo.controller.task;

import com.creams.temo.entity.JsonResult;
import com.creams.temo.entity.task.request.TaskRequest;
import com.creams.temo.entity.task.response.TaskResponse;
import com.creams.temo.service.task.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("批量执行任务")
    @PostMapping("/executeTasks")
    public JsonResult executeTasks(@RequestBody List<String> taskIds){
        try {
            if (taskIds == null || taskIds.isEmpty()){
                return new JsonResult("操作失败", 500, "任务为空", false);
            }
            for (String taskId: taskIds){
                TaskResponse taskResponse = taskService.queryTaskDetail(taskId);
                if ("1".equals(String.valueOf(taskResponse.getStatus()))){
                    return new JsonResult("不允许执行状态为执行中的任务", 500, null, false);
                }
            }
            for (String taskId: taskIds){
                TaskResponse taskResponse = taskService.queryTaskDetail(taskId);
                //判断用例集状态0和2可以进行执行
                if ("0".equals(String.valueOf(taskResponse.getStatus())) || "2".equals(String.valueOf(taskResponse.getStatus()))){
                    //判断是否为定时任务
                    if ("1".equals(taskResponse.getIsTiming())){
                        taskService.startTimingTask(taskId);

                    }else if ("0".equals(taskResponse.getIsTiming())){
                        //判断是否并行
                        if ("1".equals(taskResponse.getIsParallel())){
                            taskService.startAsnchronizeTask(taskId);
                        }else if ("0".equals(taskResponse.getIsParallel())){
                            taskService.startSynchronizeTask(taskId);
                        }
                    }
                }
            }
            return new JsonResult("已成功发起批量执行任务", 200, null, true);
        }catch (Exception e){
            return new JsonResult("操作失败", 500, e, false);
        }
    }


    @ApiOperation(value = "新增任务")
    @PostMapping("")
    public JsonResult addTask(@RequestBody TaskRequest task) {
        try {
            taskService.addTask(task);
            return new JsonResult("操作成功", 200, null, true);
        } catch (Exception e) {
            return new JsonResult("操作失败", 500, e, false);
        }

    }

    @ApiOperation(value = "根据任务名和执行状态查询任务")
    @GetMapping("")
    public JsonResult queryTasks(@RequestParam(value = "taskName", required = false) String taskName, @RequestParam(value = "status", required = false) Integer status) {
        List<TaskResponse> taskResponses = taskService.queryTasks(taskName, status);
        return new JsonResult("操作成功", 200, taskResponses, true);
    }


    @ApiOperation(value = "查询任务详情")
    @GetMapping("/{taskId}")
    public JsonResult queryTaskDetail(@PathVariable("taskId") String taskId) {
        TaskResponse taskResponse = taskService.queryTaskDetail(taskId);
        return new JsonResult("操作成功", 200, taskResponse, true);
    }


    @ApiOperation(value = "编辑任务")
    @PutMapping("/{taskId}")
    public JsonResult updateTask(@PathVariable("taskId") String taskId, @RequestBody TaskRequest taskRequest) {
        try {
            taskService.updateTask(taskRequest);
            return new JsonResult("操作成功", 200, null, true);
        } catch (Exception e) {
            return new JsonResult("操作失败", 500, e, false);
        }
    }


    @ApiOperation(value = "删除任务")
    @DeleteMapping("/{taskId}")
    public JsonResult deleteTask(@PathVariable("taskId") String taskId) {
        try {
            taskService.deleteTask(taskId);
            return new JsonResult("操作成功", 200, null, true);
        } catch (Exception e) {
            return new JsonResult("操作失败", 500, e, false);
        }

    }


    @ApiOperation(value = "发起任务")
    @PostMapping("/start/{taskId}")
    public JsonResult startTask(@PathVariable("taskId") String taskId) {
        TaskResponse taskResponse = taskService.queryTaskDetail(taskId);
        String isParallel = taskResponse.getIsParallel();
        String isTiming = taskResponse.getIsTiming();
        // 判断是否是定时任务
        if ("0".equals(isTiming)) {
            // 判断是并发执行还是同步执行
            if ("0".equals(isParallel)) {
                taskService.startSynchronizeTask(taskId);
                return new JsonResult("已成功发起同步任务", 200, null, true);
            } else {
                taskService.startAsnchronizeTask(taskId);
                return new JsonResult("已成功发起异步任务", 200, null, true);
            }
        } else {
            // 发起定时任务
            taskService.startTimingTask(taskId);
            return new JsonResult("已成功发起定时任务", 200, null, true);
        }

    }

}
