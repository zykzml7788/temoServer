package com.creams.temo.controller.testcase;

import com.creams.temo.entity.JsonResult;
import com.creams.temo.entity.testcase.request.TestCaseSetRequest;
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
import java.util.List;
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
                                                  @ApiParam(value = "项目id") String projectId,
                                                  @RequestParam(value = "set_status", required = false)
                                                  @ApiParam(value = "用例集状态")String setStatus){
        try {
            PageInfo<TestCaseSetResponse> pageInfo = testCaseSetService.queryTestCaseSetByNameAndId(page, setName, projectId, setStatus);
            Map<String, Object> map = new HashMap<>();
            map.put("list", pageInfo.getList());
            map.put("total", pageInfo.getTotal());
            return new JsonResult("操作成功", 200, map, true);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("操作失败", 500, null, false);
        }

    }

    @ApiOperation(value = "查询用例集列表")
    @GetMapping (value = "/list")
    public JsonResult queryTestCaseSetByStatus(){
        try {
            return new JsonResult("操作成功", 200, testCaseSetService.queryAllTestCaseSet(), true);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("操作失败", 500, null, false);
        }

    }

    @ApiOperation(value = "根据set_id获取用例集详情")
    @GetMapping (value = "/{setId}/info")
    public JsonResult queryTestCaseSet(@PathVariable @ApiParam("集合id") String setId){
        try {
            TestCaseSetResponse testCaseSetResponse = testCaseSetService.queryTestCaseSetInfo(setId);
            return new JsonResult("操作成功", 200, testCaseSetResponse, true);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("操作失败", 500, null, false);
        }

    }

    @ApiOperation(value = "查询用例集(已废弃)")
    @PostMapping (value = "/{page}/discard")
    public JsonResult queryTestCaseSet(@PathVariable(value = "page") Integer page,
                                       @RequestBody (required = false) TestCaseSetRequest testCaseSetRequest){
        try {
            PageInfo<TestCaseSetResponse> pageInfo = testCaseSetService.queryTestCaseSet(page, testCaseSetRequest);
            Map<String, Object> map = new HashMap<>();
            map.put("list", pageInfo.getList());
            map.put("total", pageInfo.getTotal());
            return new JsonResult("操作成功", 200, map, true);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("操作失败", 500, null, false);
        }

    }

    @ApiOperation(value = "新增用例集")
    @PostMapping(value = "/")
    public JsonResult addTestCaseSet(@RequestBody TestCaseSetRequest testCaseSetRequest){
        try {
            String setId = testCaseSetService.addTestCaseSet(testCaseSetRequest);
            return new JsonResult("操作成功", 200, setId, true);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("操作失败", 500, null, false);
        }
    }

    @ApiOperation(value = "修改用例集")
    @PutMapping(value = "/{id}")
    public JsonResult updateTestCaseSet(@RequestBody TestCaseSetRequest testCaseSetRequest){
        try {
            testCaseSetService.updateTestCaseSetById(testCaseSetRequest);
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
