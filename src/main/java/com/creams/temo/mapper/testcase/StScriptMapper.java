package com.creams.temo.mapper.testcase;

import com.creams.temo.entity.testcase.response.StScriptResponse;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StScriptMapper {

    List<StScriptResponse> queryStScript();

    boolean addStScript(StScriptResponse stScriptResponse);

    boolean updateStScriptById(StScriptResponse stScriptResponse);

    @Delete("delete from st_script where st_script_id = #{st_script_id}")
    boolean deleteStScript(@Param("st_script_id") String stScriptId);
}
