package com.creams.temo.controller.database;

import com.creams.temo.entity.JsonResult;
import com.creams.temo.entity.database.request.ScriptRequest;
import com.creams.temo.entity.database.response.ScriptResponse;
import com.creams.temo.entity.project.response.ProjectResponse;
import com.creams.temo.service.database.ScriptService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 项目控制层
 */

@RestController
@Api("ScriptController Api")
@RequestMapping(value = "/script")
public class ScriptController {

    @Autowired
    private ScriptService scriptService;


    @ApiOperation(value = "模糊查询脚本列表", notes = "分页查询脚本")
    @GetMapping(value = "/queryAllScript")
    public JsonResult queryAllScript(@RequestParam(defaultValue = "0") Integer page,
                                     @RequestParam(value = "filter", required = false)
                                         @ApiParam(value = "查询条件") String filter){

        try {
            if (filter == null){
                filter = "";
            }
            PageInfo<ScriptResponse> pageInfo = scriptService.queryAllScript(page, filter);
            Map<String,Object> map = new HashMap<>();
            map.put("list",pageInfo.getList());
            map.put("total",pageInfo.getTotal());
            return new JsonResult("操作成功",200,map,true);

        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("操作失败",500,null,false);
        }

    }


    @ApiOperation("查询脚本详情")
    @GetMapping(value = "/queryScriptById/{id}")
    public JsonResult queryScriptById(@PathVariable("id") @ApiParam("脚本id") String scriptId){

        try {
            ScriptResponse scriptResponse = scriptService.queryScriptById(scriptId);
            if (scriptResponse != null){
                return new JsonResult("操作成功",200,scriptResponse,true);
            }else {
                return new JsonResult("数据为空",200,null,true);
            }

        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("操作失败",500,null,false);
        }

    }

    @ApiOperation("新增脚本")
    @PostMapping(value = "/addScript")
    public JsonResult addScript(@RequestBody ScriptRequest scriptRequest){
        try {
            String scriptId = scriptService.addScript(scriptRequest);
            return new JsonResult("操作成功", 200, scriptId, true);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("操作失败",500,null,false);
        }


    }

    @ApiOperation("修改脚本")
    @PutMapping(value = "updateScriptById/{id}")
    public JsonResult updateScriptById( @RequestBody ScriptRequest scriptRequest){
        try {
            scriptService.updateScriptById(scriptRequest);
            return new JsonResult("操作成功", 200, null, true);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("操作失败",500,null,false);
        }

    }


    @ApiOperation("删除脚本")
    @DeleteMapping(value = "/deleteDatabaseById/{id}")
    public JsonResult deleteScriptById(@PathVariable("id") @ApiParam("脚本id") String scriptId){
        try {
            scriptService.deleteScriptById(scriptId);
            return new JsonResult("操作成功", 200, null, true);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("操作失败",500,null,false);
        }

    }



}
