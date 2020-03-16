package com.creams.temo.mapper.sys;

import com.creams.temo.entity.sys.Role;
import com.creams.temo.entity.sys.UserEntity;
import com.creams.temo.entity.sys.request.RoleRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author ronin
 */
@Mapper
public interface RoleMapper {

    @Select("select * from role")
    List<Role> queryRoles();

    @Select("select * from role where role_id = #{role_id}")
    UserEntity queryRoleByRoleId(@Param("role_id") String roleId);

    @Select("select * from role where user_id = #{user_id}")
    List<Role> queryRolesByUserId(@Param("user_id") String userId);

    @Update("update role set status = #{status} where role_id = #{role_id}")
    boolean setRoleStatus(@Param("role_id") String RoleId, @Param("status") Integer status);

    boolean updateRole(RoleRequest role);

    boolean addRole(RoleRequest role);
}
