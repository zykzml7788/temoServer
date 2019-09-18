package com.creams.temo.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.creams.temo.entity.project.Database;
import com.creams.temo.entity.project.request.DatabaseRequest;
import com.creams.temo.entity.project.response.DatabaseResponse;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DatabaseMapper extends BaseMapper<Database> {

    @Select("SELECT * FROM db")
    List<DatabaseResponse> queryDatabase();

    @Select("SELECT * FROM db WHERE db_id= #{db_id} ")
    Database queryDatabaseById(@Param("db_id") int dbId);


    boolean addDatabase(DatabaseRequest databaseRequest);

    boolean updateDatabaseById(DatabaseRequest databaseRequest);

    @Delete("delete from db where db_id= #{db_id}")
    boolean deteleDatabaseById(@Param("db_id") int dbId);


}
