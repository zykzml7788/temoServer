package com.creams.temo.mapper.testcase;

import com.creams.temo.entity.testcase.request.SavesRequest;
import com.creams.temo.entity.testcase.response.SavesResponse;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SavesMapper {

    List<SavesResponse> querySaves(@Param("case_id") String caseId);

    boolean addSaves(SavesRequest savesRequest);

    boolean updateSavesById(SavesRequest savesRequest);

    @Delete("delete from saves where case_id = #{case_id}")
    boolean deleteSaves(@Param("case_id") String caseId);
}