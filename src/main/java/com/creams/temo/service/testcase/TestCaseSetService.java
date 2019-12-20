package com.creams.temo.service.testcase;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import com.creams.temo.entity.project.response.EnvResponse;
import com.creams.temo.entity.testcase.request.StScriptRequest;
import com.creams.temo.entity.testcase.request.StScriptRequests;
import com.creams.temo.entity.testcase.request.TestCaseSetRequest;
import com.creams.temo.entity.testcase.response.SavesResponse;
import com.creams.temo.entity.testcase.response.TestCaseResponse;
import com.creams.temo.entity.testcase.response.TestCaseSetResponse;
import com.creams.temo.entity.testcase.response.VerifyResponse;
import com.creams.temo.mapper.database.ScriptMapper;
import com.creams.temo.mapper.project.EnvMapper;
import com.creams.temo.mapper.testcase.StScriptMapper;
import com.creams.temo.mapper.testcase.TestCaseMapper;
import com.creams.temo.mapper.testcase.TestCaseSetMapper;
import com.creams.temo.util.RedisUtil;
import com.creams.temo.util.StringUtil;
import com.creams.temo.util.WebClientUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TestCaseSetService {

    @Autowired
    TestCaseSetMapper testCaseSetMapper;

    @Autowired
    StScriptMapper stScriptMapper;

    @Autowired
    TestCaseMapper testCaseMapper;

    @Autowired
    ScriptMapper scriptMapper;

    @Autowired
    EnvMapper envMapper;

    @Autowired
    RedisUtil redisUtil;

    /**
     * 查询用例集
     * @param page
     * @param testCaseSetRequest
     * @return
     */
    @Transactional
    public PageInfo<TestCaseSetResponse> queryTestCaseSet(Integer page, TestCaseSetRequest testCaseSetRequest){
        PageHelper.startPage(page, 10);
        List<TestCaseSetResponse> testCaseSet = testCaseSetMapper.queryTestCaseSet(testCaseSetRequest);
        PageInfo<TestCaseSetResponse> pageInfo = new PageInfo<>(testCaseSet);
        return pageInfo;
    }

    /**
     * 查询用例集列表
     * @return
     */
    @Transactional
    public List<TestCaseSetResponse> queryAllTestCaseSet(){
        List<TestCaseSetResponse> testCaseSetResponses = testCaseSetMapper.queryAllTestCaseSet();
        return testCaseSetResponses;
    }

    /**
     * 批量新增前后置脚本
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String addTestCaseSetStScript(StScriptRequests stScriptRequests){

        stScriptMapper.deleteStScript(stScriptRequests.getSetId());

        if (!stScriptRequests.getStScriptRequests().isEmpty()){
            for (StScriptRequest st: stScriptRequests.getStScriptRequests()
            ) {
                st.setStScriptId(StringUtil.uuid());
                stScriptMapper.addStScript(st);
            }

        }
        return stScriptRequests.getSetId();

    }

    /**
     * 根据用例集name,项目id,状态查询用例集
     * @param page
     * @param setName
     * @param projectId
     * @return
     */
    @Transactional
    public PageInfo<TestCaseSetResponse> queryTestCaseSetByNameAndId(Integer page, String setName,String projectId,String setStatus){
        PageHelper.startPage(page, 10);
        List<TestCaseSetResponse> testCaseSet = testCaseSetMapper.queryTestCaseSetByNameandId(setName, projectId, setStatus);
        PageInfo<TestCaseSetResponse> pageInfo = new PageInfo<>(testCaseSet);
        return pageInfo;
    }

    /**
     * 新增集合
     * @param testCaseSetRequest
     * @return
     */
    @Transactional
    public String addTestCaseSet(TestCaseSetRequest testCaseSetRequest){
        String setId = StringUtil.uuid();
        testCaseSetRequest.setSetId(setId);
        testCaseSetMapper.addTestCaseSet(testCaseSetRequest);
        return setId;
    }

    /**
     * 修改用例集
     * @param testCaseSetRequest
     * @return
     */
    @Transactional
    public Boolean updateTestCaseSetById(TestCaseSetRequest testCaseSetRequest){
        boolean result;
        result = testCaseSetMapper.updateTestCaseSetById(testCaseSetRequest);
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

    /**
     * 查询用例集详情
     * @param setId
     * @return
     */
    public TestCaseSetResponse queryTestCaseSetInfo(String setId){
        TestCaseSetResponse testCaseSetResponse = testCaseSetMapper.queryTestCaseSetById(setId);
        testCaseSetResponse.setStScript(stScriptMapper.queryStScriptBySetId(setId));
        testCaseSetResponse.setTestCase(testCaseMapper.queryTestCaseBySetId(setId));
        return testCaseSetResponse;
    }

    /**
     * 执行用例集
     * @param setId
     * @param envId
     */
    public Object executeSet(String setId, String envId) {
        TestCaseSetResponse testCaseSet = this.queryTestCaseSetInfo(setId);
        EnvResponse env = envMapper.queryEnvById(envId);
        // 获取用例集下全部的用例
        List<TestCaseResponse> testCases = testCaseSet.getTestCase();
        Map<String,String> globalCookies = new HashMap<>();
        Map<String,String> globalHeaders = new HashMap<>();
        WebClientUtil webClientUtil = null;
        if (env.getPort()==null){
            webClientUtil  = new WebClientUtil(env.getHost(),globalHeaders,globalCookies);
        }else {
            webClientUtil  = new WebClientUtil(env.getHost(),globalHeaders,globalCookies);
        }

        for (TestCaseResponse testCase:testCases){
            String url = testCase.getUrl();
            String method = testCase.getMethod();
            String body = testCase.getBody();
            String gCookies = testCase.getGlobalCookies();
            String gHeaders = testCase.getGlobalHeaders();
            String delayTime = testCase.getDelayTime();
            String jsonAssert = testCase.getJsonAssert();
            String caseType = testCase.getCaseType();
            String cookies = testCase.getCookies();
            String headers = testCase.getHeader();
            String sqlScript = testCase.getSqlScript();
            String param = testCase.getParam();
            String dbId = testCase.getDbId();
            String contentType = testCase.getContentType();
            List<SavesResponse> saves = testCase.getSaves();
            List<VerifyResponse> verifys = testCase.getVerify();

            // 判断是否有全局Cookie或者全局Header，如果有则重新生成webclient实例
            if (gCookies != null &&  !"".equals(gCookies)){
                Map<String,String> maps = (HashMap<String,String>) JSON.parse(gCookies);
                for (Map.Entry<String,String> kvs : maps.entrySet()){
                    String key = kvs.getKey();
                    String value = kvs.getValue();
                    globalCookies.put(key,value);
                }
                webClientUtil = new WebClientUtil(env.getHost(),globalHeaders,globalCookies);
            }
            if (gHeaders != null &&  !"".equals(gHeaders)){
                Map<String,String> maps = (HashMap<String,String>) JSON.parse(gHeaders);
                for (Map.Entry<String,String> kvs : maps.entrySet()){
                    String key = kvs.getKey();
                    String value = kvs.getValue();
                    globalHeaders.put(key,value);
                }
                webClientUtil = new WebClientUtil(env.getHost(),globalHeaders,globalCookies);
            }

            // 判断是否有附带请求头或者cookie
            Map<String,String> cookiesKV = new HashMap<>();
            Map<String,String> headersKV = new HashMap<>();

            if (cookies != null &&  !"".equals(cookies)){
                cookiesKV = (HashMap<String,String>) JSON.parse(cookies);
            }
            if (headers != null &&  !"".equals(headers)){
                headersKV = (HashMap<String,String>) JSON.parse(cookies);
            }
            // 判断请求体是否为空，进行转换
            Map<String,String> bodyKV = new HashMap<>();
            if (body != null &&  !"".equals(body)){
                bodyKV = (HashMap<String,String>) JSON.parse(body);
            }
            ClientResponse response = null;
            if ("get".equals(method.toLowerCase())){
                response = webClientUtil.get(url,headersKV, cookiesKV);
            }else if ("post".equals(method.toLowerCase())){
                // 判断表单提交 or JSON
                if ("1".equals(contentType)){
                    LinkedMultiValueMap<String, String> linkedMultiValueMap = new LinkedMultiValueMap<>();
                    for (Map.Entry<String,String> kvs : bodyKV.entrySet()){
                        linkedMultiValueMap.add(kvs.getKey(),kvs.getValue());
                    }
                    response = webClientUtil.postByFormData(url,linkedMultiValueMap,headersKV,cookiesKV);
                }else if ("2".equals(contentType)){
                    LinkedMultiValueMap<String, String> linkedMultiValueMap = new LinkedMultiValueMap<>();
                    response = webClientUtil.postByFormData(url,linkedMultiValueMap,headersKV,cookiesKV);
                }else if ("3".equals(contentType)){
                    response = webClientUtil.postByJson(url,body,headersKV,cookiesKV);
                }else {
                    return "暂不支持该POST请求格式";
                }
            }else if ("put".equals(method.toLowerCase())){
                response = webClientUtil.put(url,body,headersKV, cookiesKV);
            }else if ("delete".equals(method.toLowerCase())){
                response = webClientUtil.delete(url,headersKV, cookiesKV);
            }else {
                return "暂不支持该请求方式";
            }
            // 获取响应相关信息
            HttpStatus statusCode = response.statusCode();
            String responseHeaders =  response.headers().asHttpHeaders().toString();
            String responseCookies = response.cookies().toString();
            String responseBody = response.bodyToMono(String.class).block();

            // 处理关联参数
            for (SavesResponse save:saves){
                String paramKey = save.getParamKey();
                String jsonpath = save.getJexpression();
                String regex = save.getRegex();
                String saveFrom = save.getSaveFrom();
                String saveType = save.getSaveType();
                if ("1".equals(saveFrom)){
                    if ("1".equals(saveType)){
                        String value = (String)JSONPath.read(responseBody,jsonpath);
                        redisUtil.set(paramKey,value);
                    }else {
                        saveRegexParamToRedis(responseBody,paramKey,regex);
                    }
                }else if ("2".equals(saveFrom)){
                    if ("1".equals(saveType)){
                        String value = (String)JSONPath.read(responseHeaders,jsonpath);
                        redisUtil.set(paramKey,value);
                    }else {
                        saveRegexParamToRedis(responseHeaders,paramKey,regex);
                    }
                }else if ("3".equals(saveFrom)){
                    if ("1".equals(saveType)){
                        String value = (String)JSONPath.read(responseCookies,jsonpath);
                        redisUtil.set(paramKey,value);
                    }else {
                        saveRegexParamToRedis(responseCookies,paramKey,regex);
                    }
                }else{
                    return "不支持从该响应类型取值";
                }
            }


        }
        return null;
    }

    /**
     * 正则匹配，保存参数到redis（默认取到第一个匹配项）
     * @param target
     * @param regex
     */
    private void saveRegexParamToRedis(String target,String key,String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(target);
        String value = "";
        if(matcher.find()) {
            value = matcher.group(0);
        }
        redisUtil.set(key,value);
    }
}
