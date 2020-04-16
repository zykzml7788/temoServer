package com.creams.temo.mapper.task;

import com.creams.temo.entity.testcase.ExecutedRow;
import com.creams.temo.entity.task.response.ExecutedRowResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ExecuteRowMapper {

    boolean addExecutedRow(ExecutedRow executedRow);

    Integer queryMaxIndexOfExecutedRow(String setId);

    List<ExecutedRowResponse> queryExecutedRowBySetId(String setId);
}
