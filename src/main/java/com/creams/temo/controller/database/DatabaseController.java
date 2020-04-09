package com.creams.temo.controller.database;

import com.creams.temo.entity.JsonResult;
import com.creams.temo.entity.database.request.DatabaseRequest;
import com.creams.temo.entity.database.response.DatabaseResponse;
import com.creams.temo.service.database.DatabaseService;
import com.creams.temo.service.database.SqlExecuteService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import java.util.HashMap;
import java.util.List;



/**
 * 数据库配置管理控制层
 */

@RestController
@Api("DatabaseController Api")
@RequestMapping(value = "/database")
public class DatabaseController {

    @Autowired
    private DatabaseService databaseService;

    @Autowired
    private SqlExecuteService sqlExecuteService;

    @ApiOperation("查询所有数据库信息")
    @GetMapping(value = "/")
    public JsonResult queryAllDatabase(){
        try{
            List<DatabaseResponse> databaseResponses = databaseService.queryAllDatabase();
            return new JsonResult("操作成功", 200, databaseResponses, true);
        }catch (Exception e ){
            return new JsonResult("操作失败",500,null,false);
        }
    }

    @ApiOperation("模糊查询数据库列表")
    @GetMapping(value = "/{page}")
    public JsonResult queryDatabaseByName(@PathVariable(required = false) @ApiParam("页数") Integer page,
                                       @RequestParam(value = "filter",required = false) @ApiParam("查询条件") String filter){
        if (filter == null){
            filter = "";
        }
        try {
            PageInfo<DatabaseResponse> pageInfo = databaseService.queryDatabaseByName(page, filter);
            HashMap<String,Object> map = new HashMap<>();
            map.put("list", pageInfo.getList());
            map.put("total", pageInfo.getTotal());
            return new JsonResult("操作成功", 200, map, true);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("操作失败",500,null,false);
        }

    }


    @ApiOperation("查询数据库详情")
    @GetMapping(value = "/{id}/info")
    public JsonResult queryDatabaseById(@PathVariable("id") @ApiParam("数据库id") String dbId){

        try {
            DatabaseResponse databaseResponse = databaseService.queryDatabaseById(dbId);
            if (databaseResponse != null){
                return new JsonResult("操作成功",200,databaseResponse,true);
            }else {
                return new JsonResult("数据为空",200,null,true);
            }

        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("操作失败",500,null,false);
        }

    }

    @ApiOperation("新增数据库配置")
    @PostMapping(value = "/")
    public JsonResult addDatabase(@RequestBody DatabaseRequest databaseRequest){
        try {
            String dbId = databaseService.addDatabase(databaseRequest);
            return new JsonResult("操作成功", 200, dbId, true);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("操作失败",500,null,false);
        }


    }

    @ApiOperation("修改数据库")
    @PutMapping(value = "/{id}")
    public JsonResult updateDatabaseById(@PathVariable("id") @ApiParam("数据库id") String dbId, @RequestBody DatabaseRequest databaseRequest){
        try {
            databaseService.updateDatabaseById(databaseRequest);
            return new JsonResult("操作成功", 200, null, true);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("操作失败",500,null,false);
        }

    }


    @ApiOperation("删除数据库")
    @DeleteMapping(value = "/{id}")
    public JsonResult deleteDatabaseById(@PathVariable("id") @ApiParam("数据库id") String dbId){
        try {
            databaseService.deleteDabaseById(dbId);
            return new JsonResult("操作成功", 200, null, true);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("操作失败",500,null,false);
        }

    }

    @ApiOperation("测试数据库连接")
    @PostMapping(value = "/testConnect")
    public JsonResult testConnect(@RequestBody DatabaseRequest databaseRequest){
        try{
            sqlExecuteService.testConnect(databaseRequest);
            return new JsonResult("连接成功", 200, null, true);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("连接失败",500,e.getMessage(),false);
        }
    }



}
