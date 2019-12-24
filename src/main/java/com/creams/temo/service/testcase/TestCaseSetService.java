package com.creams.temo.service.testcase;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.alibaba.fastjson.TypeReference;
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
import com.creams.temo.service.WebSocketServer;
import com.creams.temo.util.RedisUtil;
import com.creams.temo.util.StringUtil;
import com.creams.temo.util.WebClientUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.assertj.core.api.Assertions;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.client.ClientResponse;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Autowired
    WebSocketServer webSocketServer;

    private static Logger logger = LoggerFactory.getLogger("fileInfoLog");

    /**
     * 替换符，如果数据中包含“${}”则会被替换成公共参数中存储的数据
     */
    private Pattern replaceParamPattern = Pattern.compile("\\$\\{(.*?)\\}");

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
     * @param setId 用例集ID
     * @param envId 调试环境ID
     */
    @Async
    public Object executeSet(String setId, String envId) throws Exception {
        TestCaseSetResponse testCaseSet = this.queryTestCaseSetInfo(setId);
        EnvResponse env = envMapper.queryEnvById(envId);
        // 获取用例集下全部的用例
        List<TestCaseResponse> testCases = testCaseSet.getTestCase();
        // 获取用例数量
        int casesNum = testCases.size();
        // 设置初始成功用例数为 0
        int success = 0;
        // 设置初始失败用例数为 0
        int error = 0;
        Map<String,String> globalCookies = new HashMap<>();
        Map<String,String> globalHeaders = new HashMap<>();
        WebClientUtil webClientUtil;
        if (env.getPort()==null){
            webClientUtil  = new WebClientUtil(env.getHost()+":"+env.getPort(),globalHeaders,globalCookies);
        }else {
            webClientUtil  = new WebClientUtil(env.getHost(),globalHeaders,globalCookies);
        }
        // 遍历所有用例集合
        for (TestCaseResponse testCase:testCases){
            int index = testCases.indexOf(testCase)+1;
            logger.info(String.format("正在执行第%s条用例...",index));
            // 取到用例相关信息，并处理${key}的关联部分
            String url = getCommonParam(testCase.getUrl());
            // 判断是否是http或者https开头，如果是则重新生成webclient实例
            if (url.startsWith("http") || url.startsWith("https")){
                logger.info("url是http or https开头，重新生成webclient实例");
                webClientUtil = new WebClientUtil("",globalHeaders,globalCookies);
            }else{
                if (env.getPort()!=null){
                    webClientUtil  = new WebClientUtil(env.getHost()+":"+env.getPort(),globalHeaders,globalCookies);
                }else {
                    webClientUtil  = new WebClientUtil(env.getHost(),globalHeaders,globalCookies);
                }
            }
            String method = getCommonParam(testCase.getMethod());
            String body = getCommonParam(testCase.getBody());
            String gCookies = getCommonParam(testCase.getGlobalCookies());
            String gHeaders = getCommonParam(testCase.getGlobalHeaders());
            String delayTime = testCase.getDelayTime();
            String jsonAssert = getCommonParam(testCase.getJsonAssert());
            String caseType = testCase.getCaseType();
            String cookies = getCommonParam(testCase.getCookies());
            String headers = getCommonParam(testCase.getHeader());
            String sqlScript = getCommonParam(testCase.getSqlScript());
            String param = getCommonParam(testCase.getParam());
            String dbId = testCase.getDbId();
            String contentType = testCase.getContentType();
            List<SavesResponse> saves = testCase.getSaves();
            if (saves==null){
                saves = new ArrayList<>();
            }
            List<VerifyResponse> verifys = testCase.getVerify();
            if (verifys==null){
                verifys = new ArrayList<>();
            }
            if (delayTime!=null){
                logger.info("正在等待"+delayTime+"秒...");
                Thread.sleep(Integer.valueOf(delayTime)*1000);
            }
            // 判断是否有全局Cookie或者全局Header，如果有则重新生成webclient实例
            if (gCookies != null &&  !"".equals(gCookies)){
                Map<String,String> maps = JSON.parseObject(gCookies,new TypeReference<Map<String, String>>(){});
                for (Map.Entry<String,String> kvs : maps.entrySet()){
                    String key = getCommonParam(kvs.getKey());
                    String value = getCommonParam(kvs.getValue());
                    globalCookies.put(key,value);
                }
                webClientUtil = new WebClientUtil(env.getHost(),globalHeaders,globalCookies);
            }
            if (gHeaders != null &&  !"".equals(gHeaders)){
                Map<String,String> maps = JSON.parseObject(gHeaders,new TypeReference<Map<String, String>>(){});
                for (Map.Entry<String,String> kvs : maps.entrySet()){
                    String key = getCommonParam(kvs.getKey());
                    String value = getCommonParam(kvs.getValue());
                    globalHeaders.put(key,value);
                }
                webClientUtil = new WebClientUtil(env.getHost(),globalHeaders,globalCookies);
            }

            // 判断是否有附带请求头或者cookie
            Map cookiesKv = new HashMap<>();
            Map headersKv = new HashMap<>();

            // 判断请求体是否为空，进行转换
            Map paramKv = new HashMap<>();
            if (param != null &&  !"".equals(param)){
                paramKv = (Map) JSON.parse(param);
            }
            if (cookies != null &&  !"".equals(cookies)){
                cookiesKv = (Map) JSON.parse(cookies);
            }
            if (headers != null &&  !"".equals(headers)){
                headersKv = (Map) JSON.parse(cookies);
            }
            // 判断请求体是否为空，进行转换
            Map<String,String> bodyKV = new HashMap<>();
            if (body != null &&  !"".equals(body)){
                bodyKV = JSON.parseObject(body,new TypeReference<Map<String, String>>(){});
            }
            ClientResponse response;
            switch (method.toLowerCase()) {
                case "get":
                    response = webClientUtil.get(url, paramKv, headersKv, cookiesKv);
                    break;
                case "post":
                    // 判断表单提交 or JSON
                    if ("1".equals(contentType)) {
                        LinkedMultiValueMap<String, String> linkedMultiValueMap = new LinkedMultiValueMap<>();
                        for (Map.Entry<String, String> kvs : bodyKV.entrySet()) {
                            linkedMultiValueMap.add(kvs.getKey(), kvs.getValue());
                        }
                        response = webClientUtil.postByFormData(url, linkedMultiValueMap, headersKv, cookiesKv);
                    } else if ("2".equals(contentType)) {
                        LinkedMultiValueMap<String, String> linkedMultiValueMap = new LinkedMultiValueMap<>();
                        response = webClientUtil.postByFormData(url, linkedMultiValueMap, headersKv, cookiesKv);
                    } else if ("3".equals(contentType)) {
                        response = webClientUtil.postByJson(url, body, headersKv, cookiesKv);
                    } else {
                        logger.error("暂不支持该POST请求格式");
                        return "暂不支持该POST请求格式";
                    }
                    break;
                case "put":
                    response = webClientUtil.put(url, body, headersKv, cookiesKv);
                    break;
                case "delete":
                    response = webClientUtil.delete(url, headersKv, cookiesKv);
                    break;
                default:
                    logger.error("暂不支持该请求方式");
                    return "暂不支持该请求方式";
            }
            // 获取响应状态码
            HttpStatus statusCode = response.statusCode();
            // 处理响应体，转换为JSON字符串
            String responseBody;
            String responseHeaders;
            String responseCookies;
            try{
                responseBody = new JSONObject(response.bodyToMono(Map.class).block()).toString();
                logger.info("Response Body : " +responseBody);
                Map<String,Object> rHeaders = new HashMap<>();
                // 处理响应头，转换为JSON字符串
                for (Map.Entry<String, List<String>> entry:response.headers().asHttpHeaders().entrySet()){
                    rHeaders.put(entry.getKey(),entry.getValue());
                }
                responseHeaders = new JSONObject(rHeaders).toString();
                logger.info("Response Header : "+responseHeaders);
                // 处理响应Cookie,转换为JSON字符串
                Map<String,Object> rCookies = new HashMap<>();
                for (Map.Entry<String, List<ResponseCookie>> entry:response.cookies().entrySet()){
                    rCookies.put(entry.getKey(),entry.getValue());
                }
                responseCookies = new JSONObject(rCookies).toString();
                logger.info("Response Cookie : "+ responseCookies);
            }catch (Exception e){
                logger.error("响应JSON转换失败,请确认响应结构是否为JSON!");
                error++;
                //格式化小数
                DecimalFormat df = new DecimalFormat("0.00");
                // 计算百分比
                String num = df.format(((float)index/casesNum)*100);
                WebSocketServer.sendInfo(String.format("已执行用例数:%d,成功数:%d,失败数:%d,已执行用例数百分比：%s %%,总用例数:%d"
                        ,index,index-error,error,num,casesNum),"123");
                continue;
            }

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
                    logger.error("不支持从该响应类型取值");
                    return "不支持从该响应类型取值";
                }
            }
            // 设置默认总体断言结果为真
            boolean verifyResult = true;
            // 遍历断言集合，进行断言
            for (VerifyResponse verify:verifys){
                String verifyType = verify.getVerifyType();
                String expect = verify.getExpect();
                String jsonpath = verify.getJexpression();
                String regex = verify.getRexpression();
                String relationShip = verify.getRelationShip();
                // 判断断言类型是 JsonPath 还是 正则
                if ("1".equals(verifyType)){
                    String value = (String)JSONPath.read(responseBody,jsonpath);
                    try{
                        superAssert(relationShip,value,expect);
                        logger.info("断言成功！");
                    }catch (Exception e){
                        logger.error("断言失败："+e);
                        verifyResult = false;
                    }
                }else if ("2".equals(verifyType)){
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(responseBody);
                    String value = "";
                    if(matcher.find()) {
                        value = matcher.group(0);
                    }
                    try{
                        superAssert(relationShip,value,expect);
                        logger.info("断言成功！");
                    }catch (Exception e){
                        logger.error("断言失败："+e);
                        verifyResult = false;
                    }
                }else {
                    return "不支持该断言方式";
                }
            }
            if (jsonAssert!=null && !"".equals(jsonAssert)){
                // false代表非严格校验，只比较部分字段
                try{
                    JSONAssert.assertEquals(jsonAssert,responseBody,false);
                    logger.info("JSON断言成功！");
                }catch (Exception e){
                    logger.error("JSON断言失败："+e);
                    verifyResult = false;
                }
            }
            if (verifyResult){
                success++;
            }else {
                error++;
            }
            //格式化小数
            DecimalFormat df = new DecimalFormat("0.00");
            // 计算百分比
            String num = df.format(((float)index/casesNum)*100);
            WebSocketServer.sendInfo(String.format("已执行用例数:%d,成功数:%d,失败数:%d,已执行用例数百分比：%s %%,总用例数:%d"
            ,index,index-error,error,num,casesNum),"123");

        }
        return null;
    }

    /**
     * 正则匹配，保存参数到redis（默认取到第一个匹配项）
     * @param target
     * @param regex
     */
    private void saveRegexParamToRedis(String target, String key, String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(target);
        String value = "";
        if(matcher.find()) {
            value = matcher.group(0);
        }
        // 往redis存值，设置默认过期时间为一小时
        redisUtil.set(key,value,3600);
    }

    /**
     * 超级断言
     * @param type  断言方式
     * @param target  目标字符串
     * @param expect  期待字符串
     * @throws Exception
     */
    private void superAssert(String type,String target,String expect) throws Exception {
        switch (type){
            case "1":
                assertThat(target).isEqualTo(expect);
                break;
            case "2":
                assertThat(target).isNotEqualTo(expect);
                break;
            case "3":
                assertThat(target).contains(expect);
                break;
            case "4":
                assertThat(target).doesNotContain(expect);
                break;
            case "5":
                assertThat(target).isSubstringOf(expect);
                break;
            case "6":
                assertThat(target).isNotIn(expect);
                break;
            case "7":
                assertThat(target).isNull();
                break;
            case "8":
                assertThat(target).isNotNull();
                break;
            case "9":
                assertThat(Double.parseDouble(target)).isGreaterThan(Double.parseDouble(expect));
                break;
            case "10":
                assertThat(Double.parseDouble(target)).isLessThan(Double.parseDouble(expect));
                break;
            case "11":
                assertThat(Double.parseDouble(target)).isGreaterThanOrEqualTo(Double.parseDouble(expect));
                break;
            case "12":
                assertThat(Double.parseDouble(target)).isLessThanOrEqualTo(Double.parseDouble(expect));
                break;
            default:
                throw new Exception("不支持该种关系断言");
        }
    }

    /**
     * 处理${key}，从redis查询key
     * @param param 需要匹配的字符串参数
     * @return  替换${key}后的字符串参数
     */
    private String getCommonParam(String param) {
        if (StringUtil.isEmptyOrNull(param)) {
            return "";
        }
        // 取公共参数正则
        Matcher m = replaceParamPattern.matcher(param);
        while (m.find()) {
            String replaceKey = m.group(1);
            String value;
            // 从redis中获取值
            value = (String)redisUtil.get(replaceKey);
            // 如果redis中未能找到对应的值，该用例失败。
            Assertions.assertThat(value).isNotNull();
            param = param.replace(m.group(), value);
        }
        return param;
    }

    /**
     * 函数助手处理，匹配 {{func}}
     * @param param
     * @return
     */
    private String buildParam(String param) {
        return null;
    }

}
