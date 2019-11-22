package com.creams.temo.controller.testcase;

import com.creams.temo.entity.JsonResult;
import com.creams.temo.entity.testcase.response.TestCaseSetResponse;
import com.creams.temo.service.testcase.TestCaseSetService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试用例集控制层
 */
@RestController
@Api("TestCaseSetController Api")
@RequestMapping("/testcaseset")
public class TestCaseSetController {
    private static Logger logger = LoggerFactory.getLogger("fileInfoLog");

    @Autowired
    TestCaseSetService testCaseSetService;

    @ApiOperation(value = "查询用例集")
    @GetMapping (value = "/{page}")
    public JsonResult queryTestCaseSetByNameAndId(@PathVariable(value = "page") Integer page,
                                                  @RequestParam(value = "set_name", required = false)
                                                  @ApiParam(value = "用例集名字") String setName,
                                                  @RequestParam(value = "project_id", required = false)
                                                  @ApiParam(value = "项目id") String projectId){
        try {
            PageInfo<TestCaseSetResponse> pageInfo = testCaseSetService.queryTestCaseSetByNameAndId(page, setName, projectId);
            Map<String, Object> map = new HashMap<>();
            map.put("list", pageInfo.getList());
            map.put("totle", pageInfo.getTotal());
            return new JsonResult("操作成功", 200, map, true);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("操作失败", 500, null, false);
        }

    }

    @ApiOperation(value = "查询用例集(已废弃)")
    @PostMapping (value = "/{page}/discard")
    public JsonResult queryTestCaseSet(@PathVariable(value = "page") Integer page,
                                       @RequestBody (required = false) TestCaseSetResponse testCaseSetResponse){
        try {
            PageInfo<TestCaseSetResponse> pageInfo = testCaseSetService.queryTestCaseSet(page, testCaseSetResponse);
            Map<String, Object> map = new HashMap<>();
            map.put("list", pageInfo.getList());
            map.put("totle", pageInfo.getTotal());
            return new JsonResult("操作成功", 200, map, true);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("操作失败", 500, null, false);
        }

    }

    @ApiOperation(value = "新增用例集")
    @PostMapping(value = "/")
    public JsonResult addTestCaseSet(@RequestBody TestCaseSetResponse testCaseSetResponse){
        try {
            String setId = testCaseSetService.addTestCaseSet(testCaseSetResponse);
            return new JsonResult("操作成功", 200, setId, true);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("操作失败", 500, null, false);
        }
    }

    @ApiOperation(value = "修改用例集")
    @PutMapping(value = "/{id}")
    public JsonResult updateTestCaseSet(@RequestBody TestCaseSetResponse testCaseSetResponse){
        try {
            testCaseSetService.updateTestCaseSetById(testCaseSetResponse);
            return new JsonResult("操作成功", 200, null, true);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("操作失败", 500, null, false);
        }
    }

    @ApiOperation("删除用例集")
    @DeleteMapping(value = "/{id}")
    public JsonResult deleteTestCaseSet(@PathVariable("id") @ApiParam("用例集id")  String setId){
        try {
            testCaseSetService.deleteTestCaseSetById(setId);
            return new JsonResult("操作成功", 200, setId, true);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("操作失败", 500, null, false);
        }
    }

}
