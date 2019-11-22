package com.creams.temo.controller.testcase;

import com.creams.temo.entity.JsonResult;
import com.creams.temo.entity.testcase.response.TestCaseResponse;
import com.creams.temo.service.testcase.TestCaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Api("TestCaseController APi")
@RequestMapping("/testcase")
public class TestCaseController {


    @Autowired
    TestCaseService testCaseService;

    @ApiOperation(value = "新增用例")
    @PostMapping(value = "/")
    public JsonResult addTestCase(@RequestBody TestCaseResponse testCaseResponse){
        try {
            String caseId = testCaseService.addTestCase(testCaseResponse);
            return new JsonResult("操作成功", 200, caseId, true);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("操作失败", 500, null, false);
        }

    }
}
