package com.creams.temo.controller;

import com.creams.temo.entity.sys.UserEntity;
import com.creams.temo.util.ShiroUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
public class LoginController {


    @GetMapping("/login")
    public String login(UserEntity user) {
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
//            subject.checkRole("admin");
//            subject.checkPermissions("query", "add");
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return "账号或密码错误！";
        } catch (AuthorizationException e) {
            e.printStackTrace();
            return "没有权限";
        }
        return "login success";
    }

    //注解验角色和权限
    @RequiresRoles("PG")
    @RequiresPermissions("add")
    @RequestMapping("/index/add")
    public String add() {
        List list = new ArrayList();
        return "add!";
    }

    //注解验角色和权限
    @RequiresRoles("QA")
    @RequiresPermissions("query")
    @RequestMapping("/index/query")
    public String query() {
        return "query!";
    }
}
