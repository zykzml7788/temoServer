package com.creams.temo.service.testcase;


import com.creams.temo.entity.testcase.response.TestCaseSetResponse;
import com.creams.temo.mapper.testcase.TestCaseSetMapper;
import com.creams.temo.util.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TestCaseSetService {

    @Autowired
    TestCaseSetMapper testCaseSetMapper;

    /**
     * 查询用例集
     * @param page
     * @param testCaseSetResponse
     * @return
     */
    @Transactional
    public PageInfo<TestCaseSetResponse> queryTestCaseSet(Integer page, TestCaseSetResponse testCaseSetResponse){
        PageHelper.startPage(page, 10);
        List<TestCaseSetResponse> testCaseSet = testCaseSetMapper.queryTestCaseSet(testCaseSetResponse);
        PageInfo<TestCaseSetResponse> pageInfo = new PageInfo<>(testCaseSet);
        return pageInfo;
    }


    /**
     * 根据用例集name和项目id查询用例集
     * @param page
     * @param setName
     * @param projectId
     * @return
     */
    @Transactional
    public PageInfo<TestCaseSetResponse> queryTestCaseSetByNameAndId(Integer page, String setName,String projectId){
        PageHelper.startPage(page, 10);
        List<TestCaseSetResponse> testCaseSet = testCaseSetMapper.queryTestCaseSetByNameandId(setName, projectId);
        PageInfo<TestCaseSetResponse> pageInfo = new PageInfo<>(testCaseSet);
        return pageInfo;
    }

    /**
     * 新增集合
     * @param testCaseSetResponse
     * @return
     */
    @Transactional
    public String addTestCaseSet(TestCaseSetResponse testCaseSetResponse){
        String setId = StringUtil.uuid();
        testCaseSetResponse.setSetId(setId);
        testCaseSetMapper.addTestCaseSet(testCaseSetResponse);
        return setId;
    }

    /**
     * 修改用例集
     * @param testCaseSetResponse
     * @return
     */
    @Transactional
    public Boolean updateTestCaseSetById(TestCaseSetResponse testCaseSetResponse){
        boolean result;
        result = testCaseSetMapper.updateTestCaseSetById(testCaseSetResponse);
        if (result){
            return true;
        }
        return false;
    }

    /**
     * 删除用例集
     * @param setId
     * @return
     */
    @Transactional
    public Boolean deleteTestCaseSetById(String setId){
        boolean result;
        result = testCaseSetMapper.deleteTestCaseSetById(setId);
        if (result){
            return true;
        }
        return false;
    }


}
