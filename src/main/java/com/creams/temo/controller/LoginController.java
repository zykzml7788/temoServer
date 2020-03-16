package com.creams.temo.controller;

import com.alibaba.fastjson.JSONObject;
import com.creams.temo.entity.JsonResult;
import com.creams.temo.entity.sys.UserEntity;
import com.creams.temo.entity.sys.request.LoginRequest;
import com.creams.temo.util.ShiroUtils;
import io.swagger.annotations.Api;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;



@Api("LoginController Api")
@RestController
@RequestMapping("/")
public class LoginController {


    @GetMapping("login")
    public String loginByGet(LoginRequest user) {
        //添加用户认证信息
        Subject subject = ShiroUtils.getSubject();
        String shairPwd = ShiroUtils.sha256(user.getPassword(), user.getUserName());
        user.setPassword(shairPwd);
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                user.getUserName(),
                user.getPassword()
        );
        try {
            //进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(usernamePasswordToken);
            System.out.println("登录成功" +
                    "");
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return "账号或密码错误！";
        } catch (AuthorizationException e) {
            e.printStackTrace();
            return "没有权限";
        }
        return "login success";
    }

    @PostMapping("login")
    public JsonResult login(@RequestBody LoginRequest user) {
        //添加用户认证信息
        Subject subject = ShiroUtils.getSubject();
        String shairPwd = ShiroUtils.sha256(user.getPassword(), user.getUserName());
        user.setPassword(shairPwd);
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                user.getUserName(),
                user.getPassword()
        );
        try {
            //进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(usernamePasswordToken);
            return new JsonResult("登入成功",200,null,true);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return new JsonResult("账号密码错误",500,null,false);
        }
    }

    @PostMapping("/logout")
    public JsonResult login() {
        //添加用户认证信息
        Subject subject = ShiroUtils.getSubject();
        try {
            subject.logout();
            return new JsonResult("登出成功",200,null,true);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return new JsonResult("登出失败",500,null,false);
        }
    }
}
