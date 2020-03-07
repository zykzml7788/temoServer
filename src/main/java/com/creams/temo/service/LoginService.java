package com.creams.temo.service;

import com.creams.temo.entity.Permissions;
import com.creams.temo.entity.Role;
import com.creams.temo.entity.UserEntity;
import com.creams.temo.mapper.PermissionsMapper;
import com.creams.temo.mapper.RoleMapper;
import com.creams.temo.mapper.UserMapper;
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
     * @param userEntity
     * @return
     */
    public Boolean queryUser(UserEntity userEntity){
        String userId = StringUtil.uuid();
        userEntity.setUserId(userId);
        return userMapper.addUser(userEntity);
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
    public Boolean addRole(Role role){
        String roleId = StringUtil.uuid();
        role.setRoleId(roleId);
        return roleMapper.addRole(role);
    }

    /**
     * 添加权限
     * @param permissions
     * @return
     */
    public Boolean addPermissions(Permissions permissions){
        String permissionsId = StringUtil.uuid();
        permissions.setPermissionsId(permissionsId);
        return permissionsMapper.addPermissions(permissions);
    }
}
