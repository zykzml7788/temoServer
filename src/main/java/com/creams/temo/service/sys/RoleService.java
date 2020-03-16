package com.creams.temo.service.sys;

import com.creams.temo.entity.sys.Role;
import com.creams.temo.entity.sys.request.RoleRequest;
import com.creams.temo.mapper.sys.RoleMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    RoleMapper roleMapper;

    /**
     * 分页查询所有角色
     * @return
     */
    public PageInfo<Role> queryRole(Integer page){
        PageHelper.startPage(page, 10);
        List<Role> roles = roleMapper.queryRoles();
        return new PageInfo<>(roles);
    }

    /**
     * 根据用户id查询角色
     * @param userId
     * @return
     */
    public List<Role> queryRoleByUserId(String userId){
        return roleMapper.queryRolesByUserId(userId);
    }

    /**
     * 添加角色
     * @param role
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean addRole(RoleRequest role){
        if (StringUtils.isEmpty(role)){
            return false;
        }else {
            return roleMapper.addRole(role);
        }
    }

    /**
     * 设置角色状态
     * @param roleId
     * @param status
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean setRoleStatus(String roleId, Integer status){
        if (roleId.isEmpty() || "".equals(roleId)){
            return false;
        }else {
             roleMapper.setRoleStatus(roleId, status);
        }
        return true;
    }


    /**
     * 修改角色
     * @param role
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRole(RoleRequest role){
        if (StringUtils.isEmpty(role)) {
            return false;
        }else {
            return roleMapper.updateRole(role);
        }
    }
}
