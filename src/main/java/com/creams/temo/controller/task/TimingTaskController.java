package com.creams.temo.controller.task;

import com.creams.temo.entity.JsonResult;
import com.creams.temo.entity.task.request.TaskRequest;
import com.creams.temo.entity.task.request.TimingTaskRequest;
import com.creams.temo.entity.task.response.TaskResponse;
import com.creams.temo.entity.task.response.TimingTaskResponse;
import com.creams.temo.service.task.TaskService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 任务
 *
 */
@RestController
@Api("TimingTaskController APi")
@RequestMapping("/timingTask")
public class TimingTaskController {

    @Autowired
    public TaskService taskService;


    @ApiOperation(value = "新增定时任务")
    @PostMapping("")
    public JsonResult addTask(@RequestBody TimingTaskRequest task) {
        try {
            taskService.addTimingTask(task);
            return new JsonResult("操作成功", 200, null, true);
        } catch (Exception e) {
            return new JsonResult("操作失败", 500, e, false);
        }

    }

    @ApiOperation(value = "根据定时任务名和执行方式查询定时任务")
    @GetMapping("/{page}")
    public JsonResult queryTasks(@PathVariable(value = "page") Integer page,@RequestParam(value = "taskName", required = false) String taskName, @RequestParam(value = "isParallel", required = false) String isParallel) {
        PageInfo<TimingTaskResponse> pageInfo = taskService.queryTimingTasks(page,taskName, isParallel);
        HashMap<String,Object> map = new HashMap<>();
        map.put("list", pageInfo.getList());
        map.put("total", pageInfo.getTotal());
        return new JsonResult("操作成功", 200, map, true);
    }


    @ApiOperation(value = "查询定时任务详情")
    @GetMapping("/{taskId}/info")
    public JsonResult queryTaskDetail(@PathVariable("taskId") String taskId) {
        TimingTaskResponse timingTaskResponse = taskService.queryTimingTaskDetail(taskId);
        return new JsonResult("操作成功", 200, timingTaskResponse, true);
    }


    @ApiOperation(value = "编辑定时任务")
    @PutMapping("/{taskId}")
    public JsonResult updateTask(@PathVariable("taskId") String taskId, @RequestBody TimingTaskRequest timingTaskRequest) {
        try {
            taskService.updateTimingTask(timingTaskRequest);
            return new JsonResult("操作成功", 200, null, true);
        } catch (Exception e) {
            return new JsonResult("操作失败", 500, e, false);
        }
    }


    @ApiOperation(value = "删除定时任务")
    @DeleteMapping("/{taskId}")
    public JsonResult deleteTask(@PathVariable("taskId") String taskId) {
        try {
            taskService.deleteTask(taskId);
            return new JsonResult("操作成功", 200, null, true);
        } catch (Exception e) {
            return new JsonResult("操作失败", 500, e, false);
        }
    }


    @ApiOperation(value = "开启定时任务")
    @PostMapping("/start/{taskId}")
    public JsonResult startTimingTask(@PathVariable("taskId") String taskId) {
        try{
            taskService.startTimingTask(taskId);
            return new JsonResult("已成功开启定时任务", 200, null, true);
        }catch (Exception e){
            return new JsonResult("发生未知错误", 500, e, false);
        }

    }

    @ApiOperation(value = "关闭定时任务")
    @PostMapping("/close/{taskId}")
    public JsonResult closeTimingTask(@PathVariable("taskId") String taskId) {
        try{
            taskService.closeTimingTask(taskId);
            return new JsonResult("已关闭开启定时任务", 200, null, true);
        }catch (Exception e){
            return new JsonResult("发生未知错误", 500, e, false);
        }
    }


    @ApiOperation(value = "批量开启定时任务")
    @PostMapping("/startTasks/")
    public JsonResult startTimingTasks(@RequestBody List<String> taskIds) {
        try{
            for (String taskId:taskIds){
                if (taskService.queryTimingTaskDetail(taskId).getIsOpen() == 0){
                    taskService.startTimingTask(taskId);
                }
            }
            return new JsonResult("已批量开启定时任务", 200, null, true);
        }catch (Exception e){
            return new JsonResult("发生未知错误", 500, e, false);
        }

    }

    @ApiOperation(value = "批量关闭定时任务")
    @PostMapping("/closeTasks/")
    public JsonResult closeTimingTask(@RequestBody List<String> taskIds) {
        try{
            for (String taskId:taskIds){
                if (taskService.queryTimingTaskDetail(taskId).getIsOpen() == 1){
                    taskService.closeTimingTask(taskId);
                }
            }
            return new JsonResult("已批量开启定时任务", 200, null, true);
        }catch (Exception e){
            return new JsonResult("发生未知错误", 500, e, false);
        }
    }

}
