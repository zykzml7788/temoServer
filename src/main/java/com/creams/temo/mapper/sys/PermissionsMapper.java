package com.creams.temo.mapper.sys;

import com.creams.temo.entity.sys.Permissions;
import com.creams.temo.entity.sys.request.PermissionsRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface PermissionsMapper {

    @Select("select * from permissions")
    List<Permissions> queryPermissions();

    @Select("select * from permissions where permissions_id = #{permissions_id}")
    Permissions queryPermissionsByPerId(@Param("permissions_id") String permissionsId);

    @Select("select * from permissions where role_id = #{role_id}")
    List<Permissions> queryPermissionsByRoleId(@Param("role_id") String RoleId);

    @Update("update permissions set status = #{status} where permissions_id = #{permissions_id}")
    boolean setPermissionStatus(@Param("permissions_id") String permissionsId, Integer status);

    boolean updatePermission(PermissionsRequest permissions);

    boolean addPermissions(PermissionsRequest permissions);

}
