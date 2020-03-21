package com.creams.temo.service.datastatistics;

import com.creams.temo.entity.datastatistics.response.ExecuteTimeInfoResponse;
import com.creams.temo.mapper.datastatistics.ExecuteTimeInfoMapper;
import com.creams.temo.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExecuteTimeInfoService {

    @Autowired
    ExecuteTimeInfoMapper executeTimeInfoMapper;

    /**
     * 查询自动化运行信息
     * @return
     */
    public ExecuteTimeInfoResponse queryExecuteTimeInfo(){
        ExecuteTimeInfoResponse executeTimeInfoResponse = new ExecuteTimeInfoResponse();
        executeTimeInfoResponse.setExecuteTime(DateUtil.getDate(executeTimeInfoMapper.queryExecuteTime()));
        executeTimeInfoResponse.setExecuteTaskNumNow(executeTimeInfoMapper.queryExecuteTaskNumNow());
        return executeTimeInfoResponse;
    }
}
