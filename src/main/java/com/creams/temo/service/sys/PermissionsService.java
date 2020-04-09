package com.creams.temo.service.sys;

import com.creams.temo.entity.sys.Permissions;
import com.creams.temo.entity.sys.request.PermissionsRequest;
import com.creams.temo.mapper.sys.PermissionsMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class PermissionsService {

    @Autowired
    PermissionsMapper permissionsMapper;

    /**
     * 分页查询所有权限
     * @return
     */
    public PageInfo<Permissions> queryPermissions(Integer page){
        PageHelper.startPage(page, 10);
        List<Permissions> permissions = permissionsMapper.queryPermissions();
        return new PageInfo<>(permissions);
    }

    /**
     * 根据角色id查询权限
     * @param roleId
     * @return
     */
    public List<Permissions> queryPermissions(String roleId) {
        return permissionsMapper.queryPermissionsByRoleId(roleId);
    }

    /**
     * 修改权限
     * @param permissions
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePermission(PermissionsRequest permissions){
        if (StringUtils.isEmpty(permissions)){
            return false;
        }else {
            return permissionsMapper.updatePermission(permissions);
        }
    }

    /**
     * 设置权限状态
     * @param permissionsId
     * @param status
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean setPermissionStatus(String permissionsId, Integer status){
        return permissionsMapper.setPermissionStatus(permissionsId, status);
    }

    /**
     * 添加权限
     * @param permissions
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean addPermissions(PermissionsRequest permissions){
        if (StringUtils.isEmpty(permissions)){
            return false;
        }else {
            return permissionsMapper.addPermissions(permissions);
        }
    }

}
