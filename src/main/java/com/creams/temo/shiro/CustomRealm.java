package com.creams.temo.shiro;

import com.creams.temo.entity.Permissions;
import com.creams.temo.entity.Role;
import com.creams.temo.entity.UserEntity;
import com.creams.temo.mapper.UserMapper;
import com.creams.temo.service.LoginService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CustomRealm extends AuthorizingRealm {

    @Autowired
    LoginService loginService;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录用户名
        String userName = (String) principalCollection.getPrimaryPrincipal();
        //根据用户名去数据库查询用户信息
        UserEntity user = loginService.queryUserByName(userName);
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        List<Role> list = loginService.queryRoleByUserId(user.getUserId());
        //添加角色
        for (Role role: list){
            simpleAuthorizationInfo.addRole(role.getRoleName());
            //添加权限
            List<Permissions> listPer = loginService.queryPermissionsByRoleId(role.getRoleId());
            for (Permissions per: listPer
                 ) {
                simpleAuthorizationInfo.addStringPermission(per.getPermissionsName());
            }
        }

//        for (Role role : user.getRoles()) {
//            //添加角色
//            simpleAuthorizationInfo.addRole(role.getRoleName());
//            //添加权限
//            for (Permissions permissions : role.getPermissions()) {
//                simpleAuthorizationInfo.addStringPermission(permissions.getPermissionsName());
//            }
//        }
        return simpleAuthorizationInfo;
    }



    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {


        //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        //获取用户信息
        String userName = authenticationToken.getPrincipal().toString();
        UserEntity user = loginService.queryUserByName(userName);
        if (user == null) {
            //这里返回后会报出对应异常
            return null;
        } else {
            //这里验证authenticationToken和simpleAuthenticationInfo的信息
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(userName, user.getPassword().toString(), getName());
            return simpleAuthenticationInfo;
        }

    }
}
