package com.creams.temo.controller;

import com.creams.temo.entity.ExecutedRow;
import com.creams.temo.entity.JsonResult;
import com.creams.temo.mapper.MongoMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api("MongoTestController Api")
@RequestMapping("/Mongodb")
public class MongoTestController {
    @Autowired
    private MongoMapper mongoMapper;

    @PostMapping(value="/save")
    @ApiOperation(value = "保存用例日志")
    public JsonResult save(@RequestBody ExecutedRow executedRow) {
        try {
            mongoMapper.save(executedRow);
            return new JsonResult("操作成功", 200, null, true);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("操作失败", 500, null, false);
        }
    }

    /**
     * 根据用例id查询对应日志
     * @param caseId
     * @return
     */
    @ApiOperation(value = "根据caseId查询用例日志")
    @GetMapping(value="/{caseId}/info")
    public JsonResult findByCaseId(@PathVariable @ApiParam("用例id") String caseId){
        try {
            ExecutedRow executedRow= mongoMapper.findByCaseId(caseId);
            System.out.println(caseId);
            System.out.println(executedRow);
            return new JsonResult("操作成功", 200, executedRow, true);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("操作失败", 500, null, false);
        }

    }

    @PutMapping(value="/")
    @ApiOperation("根据用例id修改用例日志")
    public JsonResult update(ExecutedRow executedRow){
        try {
            mongoMapper.update(executedRow);
            return new JsonResult("操作成功", 200, executedRow.getCaseId(), true);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("操作失败", 500, null, false);
        }

    }

    @DeleteMapping(value="/{id}")
    @ApiOperation("删除用例日志")
    public JsonResult deleteById(@PathVariable("id") @ApiParam("用例id") String caseId){
        try {
            mongoMapper.deleteByCaseId(caseId);
            return new JsonResult("操作成功", 200, caseId, true);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("操作失败", 500, null, false);
        }

    }

}
