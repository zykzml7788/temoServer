package com.creams.temo.service.datastatistics;

import com.creams.temo.entity.datastatistics.response.TesSetInfoResponse;
import com.creams.temo.mapper.datastatistics.TestCaseSetInfoMaper;
import com.creams.temo.mapper.testcase.TestCaseSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestCaseSetInfoService {

    @Autowired
    TestCaseSetInfoMaper testCaseSetInfoMaper;

    /**
     * 查询用例库信息
     * @return
     */
    public TesSetInfoResponse queryTestSetInfo(){
        TesSetInfoResponse tesSetInfoResponse = new TesSetInfoResponse();
        tesSetInfoResponse.setTestCaseNum(testCaseSetInfoMaper.queryTestCaseNum());
        tesSetInfoResponse.setTestCaseSetNum(testCaseSetInfoMaper.queryTestCaseSetNum());
        return tesSetInfoResponse;
    }
}
