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
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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

    /**
     * 修改用例集
     * @param testCaseRequest
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    public boolean updateTestCase(TestCaseRequest testCaseRequest){

        String caseId = testCaseRequest.getCaseId();
        if (!caseId.isEmpty()){
            //修改用例信息
            testCaseMapper.updateTestCaseById(testCaseRequest);
            //获取关联参数
            List<SavesRequest> savesRequests = testCaseRequest.getSaves();
            if (savesRequests != null && savesRequests.size()>0){
                for (SavesRequest s: savesRequests
                ) {
                    savesMapper.updateSavesById(s);
                }
            }
            //获取断言
            List<VerifyRequest> verifyRequests = testCaseRequest.getVerify();
            if (verifyRequests != null && verifyRequests.size() > 0){
                for (VerifyRequest v: verifyRequests
                ) {
                    verifyMapper.updateVerifyById(v);
                }
            }
            return true;
        }else {
            return false;
        }
    }

    /**
     * 删除用例
     * @param caseId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean  deleteTestCase(String caseId){
        return testCaseMapper.deleteTestCase(caseId) && verifyMapper.deleteVerify(caseId) && savesMapper.deleteSaves(caseId);
    }

    /**
     * 查询用例
     * @param page
     * @param caseId
     * @param envId
     * @param setId
     * @param caseDesc
     * @param dbId
     * @param caseType
     * @return
     *  PageHelper.startPage(page, 10);
     *         List<ScriptDbResponse> scriptResponses = scriptMapper.queryScriptDb(dbId, scriptName);
     *         PageInfo<ScriptDbResponse> pageInfo= new PageInfo<>(scriptResponses);
     *         pageInfo.getList().forEach(n->n.setDb(databaseMapper.queryDatabaseById(n.getDbId())));
     *         System.out.println("打印pageinfo---" + pageInfo.getList());
     *         return new PageInfo<>(scriptResponses);
     */
    @Transactional
    public PageInfo<TestCaseResponse> queryTestCase(Integer page, String caseId, String envId, String setId,
                                                    String caseDesc, String dbId, String caseType){
        PageHelper.startPage(page, 10);
        List<TestCaseResponse> testCaseResponses = testCaseMapper.queryTestCase(caseId, envId, setId, caseDesc, dbId, caseType);
        PageInfo<TestCaseResponse> pageInfo = new PageInfo<>(testCaseResponses);
//        return pageInfo;
        pageInfo.getList().forEach(n->n.setSaves(savesMapper.querySaves("", n.getCaseId())));
        pageInfo.getList().forEach(n->n.setVerify(verifyMapper.queryVerify(n.getCaseId(),"","")));
        return new PageInfo<>(testCaseResponses);
    }
}
