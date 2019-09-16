package com.creams.temo.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.creams.temo.entity.project.Database;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DatabaseMapper extends BaseMapper<Database> {

    @Select("SELECT * FROM db")
    List<Database> queryDatabase();

    @Select("SELECT * FROM db WHERE db_id= #{db_id} ")
    Database queryDatabaseByid(@Param("db_id") int dbId);


    boolean addDatabase(Database database);

    boolean updataDatabaseById(Database database);

    @Delete("delete from db where db_id= #{db_id}")
    boolean deteleDatabaseById(@Param("db_id") int dbId);


}
