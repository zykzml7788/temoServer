package com.creams.temo.mapper.datastatistics;

import com.creams.temo.entity.datastatistics.response.ExecuteSevenDaysResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SevenDaysExecuteInfoMapper {


    ExecuteSevenDaysResponse querySevenDaysTestCaseSuccessNum();

}
