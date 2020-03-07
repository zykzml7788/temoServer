package com.creams.temo.controller;

import com.creams.temo.entity.UserEntity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class LoginController {

    @GetMapping("/login")
    public String login(UserEntity user) {
        //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        System.out.println("打印user名字:" + user.getUserName());
        System.out.println("打印user密码:" + user.getPassword());
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                user.getUserName(),
                user.getPassword()
        );
        try {
            //进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(usernamePasswordToken);
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
