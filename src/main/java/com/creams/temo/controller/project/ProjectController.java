package com.creams.temo.controller.project;

import com.creams.temo.entity.JsonResult;
import com.creams.temo.entity.project.Project;
import com.creams.temo.entity.project.response.ProjectResponse;
import com.creams.temo.service.project.ProjectService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 项目控制层
 */

@RestController
@Api("ProjectController Api")
@RequestMapping("/project")
public class ProjectController {


    private static Logger logger = LoggerFactory.getLogger("fileInfoLog");

    @Autowired
    private ProjectService projectService;

    @ApiOperation("模糊查询项目列表")
    @GetMapping("/{page}")
    public JsonResult queryProject(@PathVariable(value = "page")  Integer page, @RequestParam(value = "filter",required = false)@ApiParam(value = "查询条件") String filter){
        try{
            if (filter == null){
                filter = "";
            }
            PageInfo<ProjectResponse> pageInfo = projectService.queryByName(page,filter);
            List<ProjectResponse> projectResponseList = pageInfo.getList();
            logger.info("查询出数据：\n"+projectResponseList);
            Map<String,Object> map = new HashMap<>();
            map.put("list",pageInfo.getList());
            map.put("total",pageInfo.getTotal());
            logger.info("分页数据：\n"+map);
            return new JsonResult("操作成功",200,map,true);
        }catch (Exception e){
            logger.error(e.getMessage());
            return new JsonResult("操作失败",500,null,false);
        }

    }

    @ApiOperation("查询项目详情")
    @GetMapping("/{projectId}/info")
    public JsonResult queryDetail(@PathVariable @ApiParam("项目id")String projectId){
        try{
            ProjectResponse projectResponse = projectService.queryDetailById(projectId);
            if (projectResponse != null){
                return new JsonResult("操作成功",200,projectResponse,true);
            }else {
                return new JsonResult("操作失败",404,null,false);
            }
        }catch (Exception e){
            return new JsonResult("系统错误",500,null,false);
        }
    }

    @ApiOperation("创建项目")
    @PostMapping("")
    public JsonResult addProject(@RequestBody(required = false) Project project){
        logger.info("接受到参数："+project);
        if (project==null){
            return new JsonResult("参数为空",400,null,false);
        }
        if (project.getPname() == null || "".equals(project.getPname())){
            return new JsonResult("项目名称不能为空！",400,null,false);
        }
        try{
            String projectId = projectService.addProject(project);
            return new JsonResult("操作成功",200,projectId,true);
        }catch (Exception e){
            logger.error(e.getMessage());
            return new JsonResult("操作失败",500,null,false);
        }

    }

    @ApiOperation("编辑项目")
    @PutMapping("/{projectId}")
    public JsonResult updateProject(@PathVariable @ApiParam("项目id") String projectId,@RequestBody(required = false) @ApiParam("项目") Project project){
        logger.info("接受到参数："+project);
        project.setPid(projectId);
        if (project == null){
            return new JsonResult("参数为空",400,null,false);
        }
        if (project.getPname() == null || "".equals(project.getPname())){
            return new JsonResult("项目名称不能为空！",400,null,false);
        }
        try{
            String id = projectService.updateProjectById(projectId,project);
            return new JsonResult("操作成功",200,id,true);
        }catch (Exception e){
            logger.error(e.getMessage());
            return new JsonResult("系统错误",500,null,true);
        }
    }

    @ApiOperation("删除项目")
    @DeleteMapping("/{projectId}")
    public JsonResult delProject(@PathVariable @ApiParam("项目id") String projectId){
        try{
            Integer i = projectService.delProjectById(projectId);
            if(i>0){
                return new JsonResult("操作成功",200,null,true);
            }else{
                return new JsonResult("该项目已不存在",404,null,false);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            return new JsonResult("系统错误",500,null,false);
        }
    }
}
