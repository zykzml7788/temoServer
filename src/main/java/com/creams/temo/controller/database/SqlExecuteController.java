package com.creams.temo.controller.database;

import com.creams.temo.entity.JsonResult;
import com.creams.temo.entity.database.request.ScriptRequest;
import com.creams.temo.entity.database.response.ScriptResponse;
import com.creams.temo.service.database.SqlExecuteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Api("SqlExecuteController Api")
@RequestMapping(value = "/sqlExecute")
public class SqlExecuteController {

    @Autowired
    private SqlExecuteService sqlExecuteService;

    /**
     *
     * @param scriptRequest
     * @return
     */
    @ApiOperation("执行脚本中的sql")
    @PostMapping(value = "/")
    public JsonResult  sqlExecute(@RequestBody ScriptRequest scriptRequest) {
        Map result = sqlExecuteService.sqlExecute(scriptRequest);
        if ((Integer)result.get("error") > 0){
            return new JsonResult("操作失败", 500, result,false );
        }
        return new JsonResult("操作成功",200,result,true);
    }
}
