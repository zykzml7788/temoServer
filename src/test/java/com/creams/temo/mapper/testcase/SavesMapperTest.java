package com.creams.temo.mapper.testcase;

import com.creams.temo.entity.database.request.ScriptRequest;
import com.creams.temo.entity.database.response.ScriptDbResponse;
import com.creams.temo.entity.testcase.response.SavesResponse;
import com.creams.temo.entity.testcase.response.TestCaseResponse;
import com.creams.temo.entity.testcase.response.TestCaseSetResponse;
import com.creams.temo.mapper.database.ScriptMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class SavesMapperTest {

    @Autowired
    SavesMapper savesMapper;
    @Autowired
    ScriptMapper scriptMapper;
    @Autowired
    TestCaseSetMapper testCaseSetMapper;

    @Autowired
    TestCaseMapper testCaseMapper;

    @Test
    public void  test(){
        SavesResponse savesResponse = new SavesResponse();
        savesResponse.setCaseId("21344aaa");
        savesResponse.setJexpression("12333333");
        savesResponse.setParamKey("12333");
        savesResponse.setSaveFrom("123");
//        savesMapper.addSaves(savesResponse);
    }

    @Test
    public void  test1(){
        TestCaseSetResponse testCaseSetResponse = new TestCaseSetResponse();

        testCaseSetResponse.setSetDesc("12344");
        testCaseSetResponse.setSetId("1233");
        testCaseSetResponse.setProjectId("2333");
        testCaseSetResponse.setSetName("12345");
//        testCaseSetMapper.addTestCaseSet(testCaseSetResponse);
    }


    @Test
    public void test2(){
        ScriptRequest scriptRequest = new ScriptRequest();
        scriptRequest.setScriptId("1234");
        scriptRequest.setDbId("123");
        scriptRequest.setScriptName("33");
        scriptRequest.setSqlScript("1234");
        scriptMapper.addScript(scriptRequest);
    }

    @Test
    public void test3(){
        String caseId = "7aac00b1-dccb-497c-a2a0-f6976f1f0559";
        String envId= "";
        String setId= "";
        String caseDesc= "";
        String dbId= "";
        String caseType= "";
        List<TestCaseResponse> testCaseResponses = testCaseMapper.queryTestCase(caseId,envId,setId,caseDesc,dbId,caseType);
        for (TestCaseResponse t: testCaseResponses
             ) {
            System.out.println(t.getCaseId());

        }
    }
}