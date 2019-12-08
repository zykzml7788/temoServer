package com.creams.temo.service.testcase;

import com.creams.temo.entity.testcase.TestCase;
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

        //获取该用例集下所有用例
        List<TestCaseResponse>  list= testCaseMapper.queryTestCaseBySetId(testCaseRequest.getSetId());

        testCaseRequest.setSorting(list.size()+1);
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
     */
    public PageInfo<TestCaseResponse> queryTestCase(Integer page, String caseId, String envId, String setId,
                                                    String caseDesc, String dbId, String caseType){
        PageHelper.startPage(page, 10);
        List<TestCaseResponse> testCaseResponses = testCaseMapper.queryTestCase(caseId, envId, setId, caseDesc, dbId, caseType);
        PageInfo<TestCaseResponse> pageInfo = new PageInfo<>(testCaseResponses);
        pageInfo.getList().forEach(n->n.setSaves(savesMapper.querySaves( caseId)));
        pageInfo.getList().forEach(n->n.setVerify(verifyMapper.queryVerify(caseId)));
        return new PageInfo<>(testCaseResponses);
    }

    /**
     * 查询用例详情
     * @param id
     * @return
     */
    public TestCaseResponse queryTestCaseInfo(String id){
        TestCaseResponse testCaseResponse = testCaseMapper.queryTestCaseById(id);
        testCaseResponse.setSaves(savesMapper.querySaves(id));
        testCaseResponse.setVerify(verifyMapper.queryVerify(id));
        return testCaseResponse;
    }


    /**
     * 根据用例id查询用例
     * @param caseId
     * @return
     */
    public TestCaseResponse queryTestCaseById(String caseId){
        TestCaseResponse testCaseResponse = testCaseMapper.queryTestCaseById(caseId);
        return testCaseResponse;
    }

    /**
     * 修改用例排序
     * @param caseId
     * @param move
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String updateTestCaseOrderById(String caseId, String move){

        TestCaseResponse testCaseResponse = testCaseMapper.queryTestCaseById(caseId);
        System.out.println("打印testCaseResponse" + testCaseResponse);
        System.out.println("打印caseId" + caseId);


        if (testCaseResponse != null && "up".equals(move)){
            Integer sorting = testCaseResponse.getSorting();
            if (sorting.equals(testCaseMapper.queryMinSorting(testCaseResponse.getSetId()))) {
                return "无法上移，请重试";
            }else {
                //获取当前排序上一位的用例信息
                TestCaseResponse result = testCaseMapper.queryTestCaseUpBySorting(testCaseResponse.getSetId(), sorting);
                //更新上一位用例排序
                testCaseMapper.updateTestCaseOrderById(result.getCaseId(), sorting);
                //更新当前用例排序
                testCaseMapper.updateTestCaseOrderById(caseId, result.getSorting());
            }
        }else if (testCaseResponse != null && "down".equals(move)){
            //获取该用例的排序值
            Integer sorting = testCaseResponse.getSorting();

            if (sorting.equals(testCaseMapper.queryMaxSorting(testCaseResponse.getSetId()))){
                return "无法下移，请重试";
            }else {

                //获取当前排序下一位的用例信息
                TestCaseResponse result = testCaseMapper.queryTestCaseDownBySorting(testCaseResponse.getSetId(), sorting);
                //更新下一位用例排序
                testCaseMapper.updateTestCaseOrderById(result.getCaseId(), sorting);
                //更新当前用例排序
                testCaseMapper.updateTestCaseOrderById(caseId, result.getSorting());

            }
        }
        return caseId;
    }
}
