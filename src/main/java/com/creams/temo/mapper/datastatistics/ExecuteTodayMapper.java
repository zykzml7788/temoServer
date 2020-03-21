package com.creams.temo.mapper.datastatistics;

import com.creams.temo.entity.datastatistics.response.ExecuteTodayResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ExecuteTodayMapper {

    ExecuteTodayResponse queryExecuteTodayInfo();

    ExecuteTodayResponse queryExecuteTodayTestCaseInfo();
}

