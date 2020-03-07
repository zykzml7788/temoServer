package com.creams.temo.mapper;

import com.creams.temo.entity.Role;
import com.creams.temo.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    Boolean addRole(Role role);
}
