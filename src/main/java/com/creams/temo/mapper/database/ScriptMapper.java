package com.creams.temo.mapper.database;

import com.creams.temo.entity.database.request.ScriptRequest;
import com.creams.temo.entity.database.response.ScriptResponse;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ScriptMapper {


    List<ScriptResponse> queryAllScript(@Param("script_name") String scriptName);

    @Select("SELECT * FROM script WHERE script_id= #{script_id}")
    ScriptResponse queryScriptById(@Param("script_id") String scriptId);

    @Select("SELECT * FROM script WHERE env_id= #{env_id}")
    List<ScriptResponse> queryScriptByEnvId(@Param("env_id") String envId);

    boolean addScript(ScriptRequest scriptRequest);

    boolean updateScriptById(ScriptRequest scriptRequest);

    @Delete("delete from script where script_id= #{script_id}")
    boolean deleteScriptById(@Param("script_id")String scriptId);

}
