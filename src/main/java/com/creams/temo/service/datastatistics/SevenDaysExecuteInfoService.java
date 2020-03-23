package com.creams.temo.service.datastatistics;

import com.creams.temo.entity.datastatistics.response.ExecuteSevenDaysResponse;
import com.creams.temo.mapper.datastatistics.SevenDaysExecuteInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SevenDaysExecuteInfoService {

    @Autowired
    SevenDaysExecuteInfoMapper sevenDaysExecuteInfoMapper;

    /**
     * 查询近7日用例执行情况
     * @return
     */
    public List<ExecuteSevenDaysResponse> querySevenDaysExecuteInfo(){
        List<ExecuteSevenDaysResponse> list = sevenDaysExecuteInfoMapper.querySevenDaysTestCaseSuccessNum();
        return list;
    }
}
