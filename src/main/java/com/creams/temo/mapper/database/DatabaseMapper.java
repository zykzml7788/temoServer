package com.creams.temo.mapper.database;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.creams.temo.entity.database.Database;
import com.creams.temo.entity.database.request.DatabaseRequest;
import com.creams.temo.entity.database.response.DatabaseResponse;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DatabaseMapper extends BaseMapper<Database> {

    @Select("SELECT * FROM db order by updatetime desc")
    List<DatabaseResponse> queryAllDatabase();

    @Select("SELECT * FROM db where db_name like concat('%',#{db_name},'%') order by updatetime desc")
    List<DatabaseResponse> queryDatabase(@Param("db_name") String name);

    @Select("SELECT * FROM db WHERE db_id = #{db_id}")
    DatabaseResponse queryDatabaseById(@Param("db_id") String dbId);


    boolean addDatabase(DatabaseRequest databaseRequest);

    boolean updateDatabaseById(DatabaseRequest databaseRequest);

    @Delete("delete from db where db_id= #{db_id}")
    boolean deteleDatabaseById(@Param("db_id") String dbId);


}
