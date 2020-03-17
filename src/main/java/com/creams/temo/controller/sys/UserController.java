package com.creams.temo.controller.sys;

import com.creams.temo.entity.JsonResult;
import com.creams.temo.entity.sys.UserEntity;
import com.creams.temo.entity.sys.request.UserRequest;
import com.creams.temo.service.sys.UserService;
import com.creams.temo.util.ShiroUtils;
import com.github.pagehelper.PageInfo;
import com.mongodb.client.model.ValidationAction;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import java.util.HashMap;
import java.util.List;

@RestController
@Api("UserController Api")
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @ApiOperation("新增用户")
    @PostMapping(value = "/")
    public JsonResult addUser(@RequestBody UserRequest user){
        try {
            String shaPwd = ShiroUtils.sha256(user.getPassword(), user.getUserName());
            user.setPassword(shaPwd);
            if (userService.addUser(user)){
                return new JsonResult("操作成功", 200, null, true);
            }else {
                return new JsonResult("操作失败", 500, null, false);
            }
        }catch (Exception e){
            return new JsonResult("操作失败", 500, null, false);
        }
    }

    @ApiOperation("修改用户")
    @PutMapping(value = "/update/{id}")
    public JsonResult updateUser(@RequestBody UserRequest user){
        try {
            userService.updateUser(user);
            return new JsonResult("操作成功", 200, null, true);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("操作失败", 500, null, false);
        }
    }

    @ApiOperation("查询所有用户")
    @GetMapping(value = "/{page}")
    public JsonResult queryUsers(@PathVariable(value = "page") Integer page){
        try {
            PageInfo<UserEntity> pageInfo = userService.queryUsers(page);
            HashMap<String,Object> map = new HashMap<>();
            map.put("list", pageInfo.getList());
            map.put("total", pageInfo.getTotal());
            return new JsonResult("操作成功", 200, map, true);
        }catch (Exception e){
            return new JsonResult("操作失败", 500, null, false);
        }
    }


    @ApiOperation("更新用户状态")
    @PutMapping(value = "/{id}")
    public JsonResult setUserStatus(@PathVariable(value = "id") @ApiParam("用户id") String userId, Integer status){
        try {
            if (userService.setUserStatus(userId, status)){
                return new JsonResult("操作成功", 200, null, true);
            }else {
                return new JsonResult("操作失败", 500, null, false);
            }
        }catch (Exception e){
            return new JsonResult("操作失败", 500, null, false);
        }
    }

}
