package com.creams.temo.service;

import com.creams.temo.entity.sys.Permissions;
import com.creams.temo.entity.sys.Role;
import com.creams.temo.entity.sys.UserEntity;
import com.creams.temo.entity.sys.request.PermissionsRequest;
import com.creams.temo.entity.sys.request.RoleRequest;
import com.creams.temo.entity.sys.request.UserRequest;
import com.creams.temo.mapper.sys.PermissionsMapper;
import com.creams.temo.mapper.sys.RoleMapper;
import com.creams.temo.mapper.sys.UserMapper;
import com.creams.temo.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LoginService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    PermissionsMapper permissionsMapper;

    /**
     * 查询用户
     * @param userName
     * @return
     */
    public UserEntity queryUserByName(String userName){
        UserEntity user= userMapper.queryUserByName(userName);
        return user;
    }

    /**
     * 新增
     * @param user
     * @return
     */
    public Boolean queryUser(UserRequest user){
        String userId = StringUtil.uuid();
        user.setUserId(userId);
        return userMapper.addUser(user);
    }

    /**
     * 查询所有角色
     * @param userId
     * @return
     */
    public List<Role> queryRoleByUserId(String userId){

        return roleMapper.queryRolesByUserId(userId);
    }

    /**
     * 查询所有权限
     * @param roleId
     * @return
     */
    public List<Permissions> queryPermissionsByRoleId(String roleId){

        return permissionsMapper.queryPermissionsByRoleId(roleId);
    }


    /**
     * 添加角色
     * @param role
     * @return
     */
    public Boolean addRole(RoleRequest role){
        String roleId = StringUtil.uuid();
        role.setRoleId(roleId);
        return roleMapper.addRole(role);
    }

    /**
     * 添加权限
     * @param permissions
     * @return
     */
    public Boolean addPermissions(PermissionsRequest permissions){
        String permissionsId = StringUtil.uuid();
        permissions.setPermissionsId(permissionsId);
        return permissionsMapper.addPermissions(permissions);
    }
}
