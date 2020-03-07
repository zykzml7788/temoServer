package com.creams.temo.mapper;

import com.creams.temo.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ronin
 */
@Mapper
public interface UserMapper {
    @Select("select * from user")
    List<UserEntity> queryUsers();

    @Select("select * from user where user_name = #{user_name}")
    UserEntity queryUserByName(@Param("user_name") String userName);

    Boolean addUser(UserEntity userEntity);
}
