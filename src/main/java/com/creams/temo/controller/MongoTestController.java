package com.creams.temo.controller;

import com.creams.temo.entity.ExecutedRow;
import com.creams.temo.entity.JsonResult;
import com.creams.temo.mapper.MongoMapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api("MongoTestController Api")
@RequestMapping("/Mongodb")
public class MongoTestController {
    @Autowired
    private MongoMapper mongoMapper;

    @PostMapping(value="/save")
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
    @GetMapping(value="/")
    public JsonResult findByCaseId(String caseId){
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

    @PutMapping(value="/test3")
    public JsonResult update(ExecutedRow executedRow){
        try {
            mongoMapper.update(executedRow);
            return new JsonResult("操作成功", 200, executedRow.getCaseId(), true);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("操作失败", 500, null, false);
        }

    }

    @DeleteMapping(value="/")
    public JsonResult deleteById(String caseId){
        try {
            mongoMapper.deleteByCaseId(caseId);
            return new JsonResult("操作成功", 200, caseId, true);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("操作失败", 500, null, false);
        }

    }

}
