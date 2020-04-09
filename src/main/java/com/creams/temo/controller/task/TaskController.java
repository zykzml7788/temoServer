package com.creams.temo.controller.task;

import com.creams.temo.entity.JsonResult;
import com.creams.temo.entity.task.request.TaskRequest;
import com.creams.temo.entity.task.response.TaskResponse;
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
@Api("TaskController APi")
@RequestMapping("/task")
public class TaskController {

    @Autowired
    public TaskService taskService;


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

    @ApiOperation(value = "根据任务名和执行方式查询任务")
    @GetMapping("/{page}")
    public JsonResult queryTasks(@PathVariable(value = "page") Integer page,@RequestParam(value = "taskName", required = false) String taskName, @RequestParam(value = "isParallel", required = false) String isParallel) {
        PageInfo<TaskResponse> pageInfo = taskService.queryTasks(page,taskName, isParallel);
        HashMap<String,Object> map = new HashMap<>();
        map.put("list", pageInfo.getList());
        map.put("total", pageInfo.getTotal());
        return new JsonResult("操作成功", 200, map, true);
    }


    @ApiOperation(value = "查询任务详情")
    @GetMapping("/{taskId}/info")
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


    @ApiOperation(value = "发起普通任务")
    @PostMapping("/startTask/{taskId}")
    public JsonResult startTask(@PathVariable("taskId") String taskId) throws ExecutionException, InterruptedException {
        TaskResponse taskResponse = taskService.queryTaskDetail(taskId);
        String isParallel = taskResponse.getIsParallel();
        // 判断是并发执行还是同步执行
        if ("0".equals(isParallel)) {
            taskService.startSynchronizeTask(taskId);
            return new JsonResult("已成功发起普通任务", 200, null, true);
        } else {
            taskService.startAsnchronizeTask(taskId);
            return new JsonResult("已成功发起并发任务", 200, null, true);
        }
    }

    @ApiOperation(value = "批量发起普通任务")
    @PostMapping("/startTasks/")
    public JsonResult startTasks(@RequestBody List<String> taskIds) throws ExecutionException, InterruptedException {
        for (String taskId : taskIds){
            TaskResponse taskResponse = taskService.queryTaskDetail(taskId);
            String isParallel = taskResponse.getIsParallel();
            // 判断是并发执行还是同步执行
            if ("0".equals(isParallel)) {
                taskService.startSynchronizeTask(taskId);
            } else {
                taskService.startAsnchronizeTask(taskId);
            }
        }
        return new JsonResult("已批量发起任务", 200, null, true);
    }
}
