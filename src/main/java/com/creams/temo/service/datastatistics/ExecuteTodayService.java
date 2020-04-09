package com.creams.temo.service.datastatistics;

import com.creams.temo.entity.datastatistics.response.ExecuteTodayResponse;
import com.creams.temo.mapper.datastatistics.ExecuteTodayMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExecuteTodayService {

    @Autowired
    ExecuteTodayMapper executeTodayMapper;

    /**
     * 查询当天用例执行情况
     * @return
     */
    public ExecuteTodayResponse queryTodayExecuteTaskInfo(){
        ExecuteTodayResponse executeTodayResponse = executeTodayMapper.queryExecuteTodayInfo();
        return executeTodayResponse;
    }


    /**
     * 查询当天用例成功失败num
     * @return
     */
    public ExecuteTodayResponse queryTodayExecuteTestCaseInfo(){
        ExecuteTodayResponse executeTodayResponse = executeTodayMapper.queryExecuteTodayTestCaseInfo();
        return executeTodayResponse;
    }
}
