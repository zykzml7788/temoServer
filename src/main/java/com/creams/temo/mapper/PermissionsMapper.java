package com.creams.temo.mapper;

import com.creams.temo.entity.Permissions;
import com.creams.temo.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PermissionsMapper {

    @Select("select * from permissions")
    List<Permissions> queryPermissions();

    @Select("select * from permissions where permissions_id = #{permissions_id}")
    Permissions queryPermissionsByPerId(@Param("permissions_id") String permissionsId);

    @Select("select * from permissions where role_id = #{role_id}")
    List<Permissions> queryPermissionsByRoleId(@Param("role_id") String RoleId);

    Boolean addPermissions(Permissions permissions);

}
