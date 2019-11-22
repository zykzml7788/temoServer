package com.creams.temo.service.testcase;

import com.creams.temo.entity.testcase.request.SavesRequest;
import com.creams.temo.entity.testcase.request.TestCaseRequest;
import com.creams.temo.entity.testcase.request.VerifyRequest;
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
     * @param testCaseRequest
     * @return
     */
    @Transactional
    public String addTestCase(TestCaseRequest testCaseRequest){
        String caseId = StringUtil.uuid();
        String verifyId = StringUtil.uuid();
        String savesId = StringUtil.uuid();
        testCaseRequest.setCaseId(caseId);
        List<SavesRequest> savesRequests = testCaseRequest.getSaves();
        List<VerifyRequest> verifyRequests = testCaseRequest.getVerify();
        for (SavesRequest s: savesRequests
             ) {
            s.setCaseId(caseId);
            s.setSaveId(savesId);
            savesMapper.addSaves(s);
        }

        for (VerifyRequest v: verifyRequests
             ) {
            v.setCaseId(caseId);
            v.setVerifyId(verifyId);
            verifyMapper.addVerify(v);
        }
        testCaseMapper.addTestCase(testCaseRequest);

        return caseId;
    }
}
