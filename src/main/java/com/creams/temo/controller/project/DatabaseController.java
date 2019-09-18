package com.creams.temo.controller.project;

import com.creams.temo.entity.JsonResult;
import com.creams.temo.entity.project.Database;
import com.creams.temo.entity.project.request.DatabaseRequest;
import com.creams.temo.entity.project.response.DatabaseResponse;
import com.creams.temo.service.project.DatabaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



/**
 * 项目控制层
 */

@RestController
@Api("DatabaseController Api")
@RequestMapping(value = "/database")
public class DatabaseController {

    @Autowired
    private DatabaseService databaseService;


    @ApiOperation("查询数据库列表")
    @GetMapping(value = "/queryAllDatabase")
    public JsonResult queryAllDatabase(){
        try {
            List<DatabaseResponse> databaseResponses = databaseService.queryAllDatabase();
            if (databaseResponses != null){
                return new JsonResult("操作成功",200,databaseResponses,true);
            }else {
                return new JsonResult("数据为空",200,null,true);
            }

        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("操作失败",500,null,false);
        }

    }


    @ApiOperation("查询数据库详情")
    @GetMapping(value = "/queryDatabaseById/{id}")
    public JsonResult queryDatabaseById(@PathVariable("id") @ApiParam("数据库id") int dbId){

        try {
            Database database = databaseService.queryDatabaseById(dbId);
            if (database != null){
                return new JsonResult("操作成功",200,database,true);
            }else {
                return new JsonResult("数据为空",200,null,true);
            }

        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("操作失败",500,null,false);
        }

    }

    @ApiOperation("创建项目")
    @PostMapping(value = "/addDatabase")
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
    @PutMapping(value = "updateDatabaseById/{id}")
    public JsonResult updateDatabaseById(@PathVariable("id") @ApiParam("数据库id") int dbId, @RequestBody DatabaseRequest databaseRequest){
        try {
            databaseService.updateDatabaseById(databaseRequest);
            return new JsonResult("操作成功", 200, null, true);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("操作失败",500,null,false);
        }

    }


    @ApiOperation("删除数据库")
    @DeleteMapping(value = "/deleteDatabaseById/{id}")
    public JsonResult deleteDatabaseById(@PathVariable("id") @ApiParam("数据库id") int dbId){
        try {
            databaseService.deleteDabaseById(dbId);
            return new JsonResult("操作成功", 200, null, true);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("操作失败",500,null,false);
        }

    }



}
