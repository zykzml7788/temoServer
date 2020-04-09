package com.creams.temo.controller;

import com.creams.temo.entity.JsonResult;
import com.creams.temo.entity.sys.UserEntity;
import com.creams.temo.entity.sys.request.LoginRequest;
import com.creams.temo.service.sys.UserService;
import com.creams.temo.util.ShiroUtils;
import io.swagger.annotations.Api;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Api("LoginController Api")
@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String loginByGet(LoginRequest user) {
        //添加用户认证信息
        Subject subject = ShiroUtils.getSubject();
        UserEntity userEntity =  userService.queryUsersByName(user.getUserName());
        String shairPwd = ShiroUtils.sha256(user.getPassword(), userEntity.getUserId());
        user.setPassword(shairPwd);
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                user.getUserName(),
                user.getPassword()
        );
        try {
            //进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(usernamePasswordToken);
            System.out.println("登录成功");
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return "账号或密码错误！";
        } catch (AuthorizationException e) {
            e.printStackTrace();
            return "没有权限";
        }
        return "login success";
    }

    @PostMapping("/login")
    public JsonResult login(@RequestBody LoginRequest user) {
        //添加用户认证信息
        Subject subject = ShiroUtils.getSubject();
        UserEntity userEntity =  userService.queryUsersByName(user.getUserName());
        if (userEntity==null){
            return new JsonResult("该用户不存在！",500,null,false);
        }
        if (userEntity.getStatus() == 0){
            return new JsonResult("该用户已被禁用！请联系后台人员开启！",500,null,false);
        }
        String shairPwd = ShiroUtils.sha256(user.getPassword(), userEntity.getUserId());
        user.setPassword(shairPwd);
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                user.getUserName(),
                user.getPassword()
        );
        Map<Object, Object> userInfo = new HashMap<>();
        userInfo.put("userId",userEntity.getUserId());
        userInfo.put("userName",userEntity.getUserName());
        try {
            //进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(usernamePasswordToken);
            return new JsonResult("登入成功",200,userInfo,true);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return new JsonResult("账号密码错误",500,null,false);
        }
    }

    @PostMapping("/logout")
    public JsonResult logout() {
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
