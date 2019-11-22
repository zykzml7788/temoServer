package com.creams.temo.mapper.testcase;

import com.creams.temo.entity.testcase.response.SavesResponse;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SavesMapper {

    List<SavesResponse> querySaves();


    boolean addSaves(SavesResponse savesResponse);

    boolean updateSavesById(SavesResponse savesResponse);

    @Delete("delete from saves where save_id = #{save_id}")
    boolean deleteSaves(@Param("save_id") String saveId);
}
