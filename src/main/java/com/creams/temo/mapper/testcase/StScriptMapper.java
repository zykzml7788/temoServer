package com.creams.temo.mapper.testcase;

import com.creams.temo.entity.testcase.request.StScriptRequest;
import com.creams.temo.entity.testcase.response.StScriptResponse;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StScriptMapper {

    List<StScriptResponse> queryStScript();

    @Select("select * from st_script where set_id = #{set_id}")
    List<StScriptResponse> queryStScriptBySetId(@Param("set_id") String setId);

    boolean addStScript(StScriptRequest stScriptRequest);

    boolean updateStScriptById(StScriptRequest stScriptRequest);

    @Delete("delete from st_script where st_script_id = #{st_script_id}")
    boolean deleteStScript(@Param("st_script_id") String stScriptId);
}
