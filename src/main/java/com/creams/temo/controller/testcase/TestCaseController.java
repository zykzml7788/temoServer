package com.creams.temo.controller.testcase;

import com.creams.temo.entity.JsonResult;
import com.creams.temo.entity.testcase.request.TestCaseRequest;
import com.creams.temo.entity.testcase.response.TestCaseResponse;
import com.creams.temo.service.testcase.TestCaseService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api("TestCaseController APi")
@RequestMapping("/testcase")
public class TestCaseController {


    @Autowired
    TestCaseService testCaseService;


    @ApiOperation(value = "查询用例")
    @GetMapping(value = "/{page}")
    public JsonResult queryTestCase(@PathVariable(value = "page") Integer page,
                                    @RequestParam(value = "case_id", required = false)
                                    @ApiParam(value = "用例id") String caseId,
                                    @RequestParam(value = "evn_id", required = false)
                                    @ApiParam(value = "环境id")String envId,
                                    @RequestParam(value = "set_id", required = false)
                                    @ApiParam(value = "集合id") String setId,
                                    @RequestParam(value = "case_desc", required = false)
                                    @ApiParam(value = "用例描述") String caseDesc,
                                    @RequestParam(value = "db_id", required = false)
                                    @ApiParam(value = "数据库id") String dbId,
                                    @RequestParam(value = "case_type", required = false)
                                    @ApiParam(value = "用例类型") String caseType){
        try {
            PageInfo<TestCaseResponse> pageInfo = testCaseService.queryTestCase(page, caseId, envId, setId, caseDesc, dbId, caseType);
            Map<String, Object> map = new HashMap<>();
            map.put("list", pageInfo.getList());
            map.put("totle", pageInfo.getTotal());
            return new JsonResult("操作成功", 200, map, true);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("操作失败", 500, null, false);
        }

    }


    @ApiOperation(value = "新增用例")
    @PostMapping(value = "/")
    public JsonResult addTestCase(@RequestBody TestCaseRequest testCaseRequest){
        try {
            String caseId = testCaseService.addTestCase(testCaseRequest);
            return new JsonResult("操作成功", 200, caseId, true);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("操作失败", 500, null, false);
        }
    }


    @ApiOperation(value = "修改用例")
    @PutMapping("/{id}")
    public JsonResult updateTestCase(@RequestBody TestCaseRequest testCaseRequest){
        try {
            String caseId = testCaseRequest.getCaseId();
            testCaseService.updateTestCase(testCaseRequest);
            return new JsonResult("操作成功", 200, caseId, true);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("操作失败", 500, null, false);
        }
    }


    @ApiOperation(value = "删除用例")
    @DeleteMapping("/{id}")
    public JsonResult deleteTestCase(@PathVariable("id") @ApiParam("用例id") String caseId){
        try {
            TestCaseResponse testCaseResponse = testCaseService.queryTestCaseById(caseId);
            if (testCaseResponse == null){
                return new JsonResult("数据为空", 404, caseId, true);
            }else {
                testCaseService.deleteTestCase(caseId);
                return new JsonResult("操作成功", 200, caseId, true);
            }

        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("操作失败", 500, null, false);
        }
    }
}
