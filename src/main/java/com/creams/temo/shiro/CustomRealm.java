package com.creams.temo.shiro;

import com.creams.temo.entity.sys.Permissions;
import com.creams.temo.entity.sys.Role;
import com.creams.temo.entity.sys.UserEntity;
import com.creams.temo.service.LoginService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CustomRealm extends AuthorizingRealm {

    @Autowired
    LoginService loginService;

    /**
     * 授权(验证权限时调用)
     * 获取用户权限集合
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录用户名
        String userName = (String) principalCollection.getPrimaryPrincipal();
        //根据用户名去数据库查询用户信息
        UserEntity user = loginService.queryUserByName(userName);
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        List<Role> list = loginService.queryRoleByUserId(user.getUserId());
        //添加该用户拥有user角色
        for (Role role: list){
            simpleAuthorizationInfo.addRole(role.getRoleName());
            //添加该用户拥有query权限
            List<Permissions> listPer = loginService.queryPermissionsByRoleId(role.getRoleId());
            for (Permissions per: listPer
                 ) {
                simpleAuthorizationInfo.addStringPermission(per.getPermissionsName());
            }
        }
        return simpleAuthorizationInfo;
    }


    /**
     * 认证(登录时调用)
     * 验证用户登录
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {


        //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        //获取用户信息
        //UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String userName = authenticationToken.getPrincipal().toString();
        UserEntity user = loginService.queryUserByName(userName);

        if (user == null) {
            //这里返回后会报出对应异常
            throw new LockedAccountException("账户不存在,请联系管理员");
        } else {
            //这里验证authenticationToken和simpleAuthenticationInfo的信息
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo
                    (user,
                    user.getPassword().toString(),
                            ByteSource.Util.bytes(user.getUserId()), getName());
            return simpleAuthenticationInfo;
        }

    }
}
