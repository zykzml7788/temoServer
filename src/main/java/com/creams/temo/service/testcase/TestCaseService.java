package com.creams.temo.service.testcase;

import com.creams.temo.entity.testcase.response.SavesResponse;
import com.creams.temo.entity.testcase.response.TestCaseResponse;
import com.creams.temo.entity.testcase.response.VerifyResponse;
import com.creams.temo.mapper.testcase.SavesMapper;
import com.creams.temo.mapper.testcase.TestCaseMapper;
import com.creams.temo.mapper.testcase.VerifyMapper;
import com.creams.temo.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TestCaseService {

    @Autowired
    TestCaseMapper testCaseMapper;

    @Autowired
    VerifyMapper verifyMapper;

    @Autowired
    SavesMapper savesMapper;

    /**
     * 新增用例信息
     * @param testCaseResponse
     * @return
     */
    @Transactional
    public String addTestCase(TestCaseResponse testCaseResponse){
        String caseId = StringUtil.uuid();
        String verifyId = StringUtil.uuid();
        String savesId = StringUtil.uuid();
        testCaseResponse.setCaseId(caseId);
        List<SavesResponse> savesResponses = testCaseResponse.getSaves();
        List<VerifyResponse> verifyServices = testCaseResponse.getVerify();
        for (SavesResponse s: savesResponses
             ) {
            s.setCaseId(caseId);
            s.setSaveId(savesId);
            savesMapper.addSaves(s);
        }

        for (VerifyResponse v: verifyServices
             ) {
            v.setCaseId(caseId);
            v.setVerifyId(verifyId);
            verifyMapper.addVerify(v);
        }

        return caseId;
    }
}
