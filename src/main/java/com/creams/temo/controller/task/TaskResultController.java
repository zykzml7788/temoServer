package com.creams.temo.controller.task;

import com.creams.temo.entity.JsonResult;
import com.creams.temo.entity.task.TaskResult;
import com.creams.temo.service.task.TaskService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@Api("TaskResultController APi")
@RequestMapping("/taskResult")
public class TaskResultController {

    @Autowired
    private TaskService taskService;

    @ApiOperation(value = "根据任务名和状态查询任务执行结果")
    @GetMapping(value = "/{page}")
    public JsonResult queryTaskResults(@PathVariable("page") Integer page, @RequestParam(value = "taskName",required = false) String taskName,
                                       @RequestParam(value = "status",required = false) Integer status){
        PageInfo<TaskResult> pageInfo = taskService.queryTaskResults(page,taskName,status);
        HashMap<String,Object> map = new HashMap<>();
        map.put("list", pageInfo.getList());
        map.put("total", pageInfo.getTotal());
        return new JsonResult("操作成功", 200, map, true);
    }

    @ApiOperation(value = "根据任务执行记录细节")
    @GetMapping(value = "/{taskResultId}/detail")
    public JsonResult queryTaskResultDetail(@PathVariable(value = "taskResultId") String taskResultId){
        return new JsonResult("操作成功", 200, taskService.queryTaskResultDetail(taskResultId), true);
    }
}
