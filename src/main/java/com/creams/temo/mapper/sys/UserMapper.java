package com.creams.temo.mapper.sys;

import com.creams.temo.entity.sys.UserEntity;
import com.creams.temo.entity.sys.request.UserRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author ronin
 */
@Mapper
public interface UserMapper {

    @Select("select * from user")
    List<UserEntity> queryUsers();

    @Select("select * from user where user_id = #{user_id}")
    UserEntity queryUserById(@Param("user_id") String user_id);

    @Select("select * from user where user_name = #{user_name}")
    UserEntity queryUserByName(@Param("user_name") String userName);

    @Update("update user set password = #{password} where user_id = #{user_id}")
    boolean updateUserPwd(@Param("user_id") String userId, @Param("password") String password);

    boolean addUser(UserRequest userRequest);

    boolean updateUser(UserRequest userRequest);

    @Update("update user set status = #{status} where user_id = #{user_id}")
    boolean setUserStatus(@Param("user_id") String userId, @Param("status") Integer status);
}
