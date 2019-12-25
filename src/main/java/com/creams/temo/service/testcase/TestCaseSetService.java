package com.creams.temo.service.testcase;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.alibaba.fastjson.TypeReference;
import com.creams.temo.entity.ExecutedRow;
import com.creams.temo.entity.TestResult;
import com.creams.temo.entity.project.response.EnvResponse;
import com.creams.temo.entity.testcase.Verify;
import com.creams.temo.entity.testcase.request.StScriptRequest;
import com.creams.temo.entity.testcase.request.StScriptRequests;
import com.creams.temo.entity.testcase.request.TestCaseSetRequest;
import com.creams.temo.entity.testcase.response.SavesResponse;
import com.creams.temo.entity.testcase.response.TestCaseResponse;
import com.creams.temo.entity.testcase.response.TestCaseSetResponse;
import com.creams.temo.entity.testcase.response.VerifyResponse;
import com.creams.temo.mapper.database.ScriptMapper;
import com.creams.temo.mapper.project.EnvMapper;
import com.creams.temo.mapper.testcase.*;
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

import static com.creams.temo.util.StringUtil.log;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

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
    SavesMapper savesMapper;

    @Autowired
    VerifyMapper verifyMapper;

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
        List<TestCaseResponse> testCaseResponses = testCaseMapper.queryTestCaseBySetId(setId);
        testCaseResponses.forEach(n->{
            n.setSaves(savesMapper.querySaves(n.getCaseId()));
            n.setVerify(verifyMapper.queryVerify(n.getCaseId()));
        }
        );
        testCaseSetResponse.setTestCase(testCaseResponses);
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
        String uuid = StringUtil.uuid();
        // 获取用例集下全部的用例
        List<TestCaseResponse> testCases = testCaseSet.getTestCase();
        // 获取用例数量
        int casesNum = testCases.size();
        // 设置初始成功用例数为 0
        int success = 0;
        // 设置初始失败用例数为 0
        int error = 0;
        // 设置初始的执行记录
        String executedRow = null;
        Map<String,String> globalCookies = new HashMap<>();
        Map<String,String> globalHeaders = new HashMap<>();
        WebClientUtil webClientUtil  = new WebClientUtil(env.getHost(),env.getPort().toString(),globalHeaders,globalCookies);
        // 遍历所有用例集合
        for (TestCaseResponse testCase:testCases){
            int index = testCases.indexOf(testCase)+1;
            StringBuilder logs = new StringBuilder();
            logs.append(log("INFO",String.format("正在执行第%s条用例...",index)));
            logger.info(String.format("正在执行第%s条用例...",index));
            // 取到用例相关信息，并处理${key}的关联部分
            String url = getCommonParam(testCase.getUrl(),uuid);
            webClientUtil = new WebClientUtil(env.getHost(),env.getPort().toString(),globalHeaders,globalCookies);
            String caseName = testCase.getCaseDesc();
            String method = getCommonParam(testCase.getMethod(),uuid);
            String body = getCommonParam(testCase.getBody(),uuid);
            String delayTime = testCase.getDelayTime();
            String jsonAssert = getCommonParam(testCase.getJsonAssert(),uuid);
            String caseType = testCase.getCaseType();
            String cookies = getCommonParam(testCase.getCookies(),uuid);
            String headers = getCommonParam(testCase.getHeader(),uuid);
            String sqlScript = getCommonParam(testCase.getSqlScript(),uuid);
            String param = getCommonParam(testCase.getParam(),uuid);
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
                logs.append(log("INFO","正在等待"+delayTime+"秒..."));
                logger.info(log("INFO","正在等待"+delayTime+"秒..."));
                Thread.sleep(Integer.valueOf(delayTime)*1000);
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
                headersKv = JSON.parseObject(headers,new TypeReference<Map<String, String>>(){});
            }
            // 判断请求体是否为空，进行转换
            Map<String,String> bodyKV = new HashMap<>();
            if (body != null &&  !"".equals(body)){
                bodyKV = JSON.parseObject(body,new TypeReference<Map<String, String>>(){});
            }
            ClientResponse response = null;
            logs.append(log("INFO","开始调用接口..."));
            switch (method.toLowerCase()) {
                case "get":
                    logs.append(log("INFO","=====> GET "+url));
                    logs.append(log("INFO","=====> Param "+param));
                    logs.append(log("INFO","=====> Header "+headers));
                    logs.append(log("INFO","=====> Cookie "+cookies));
                    response = webClientUtil.get(url, paramKv, headersKv, cookiesKv);
                    break;
                case "post":
                    // 判断表单提交 or JSON
                    if ("1".equals(contentType)) {
                        LinkedMultiValueMap<String, String> linkedMultiValueMap = new LinkedMultiValueMap<>();
                        for (Map.Entry<String, String> kvs : bodyKV.entrySet()) {
                            linkedMultiValueMap.add(kvs.getKey(), kvs.getValue());
                        }
                        logs.append(log("INFO","=====> POST "+url));
                        logs.append(log("INFO","=====> FormData "+bodyKV));
                        logs.append(log("INFO","=====> Header "+headers));
                        logs.append(log("INFO","=====> Cookie "+cookies));
                        response = webClientUtil.postByFormData(url, linkedMultiValueMap, headersKv, cookiesKv);
                    } else if ("2".equals(contentType)) {
                        logs.append(log("INFO","=====> POST "+url));
                        logs.append(log("INFO","=====> Param "+param));
                        logs.append(log("INFO","=====> Header "+headers));
                        logs.append(log("INFO","=====> Cookie "+cookies));
                        LinkedMultiValueMap<String, String> linkedMultiValueMap = new LinkedMultiValueMap<>();
                        response = webClientUtil.postByFormData(url, linkedMultiValueMap, headersKv, cookiesKv);
                    } else if ("3".equals(contentType)) {
                        logs.append(log("INFO","=====> POST "+url));
                        logs.append(log("INFO","=====> Body "+body));
                        logs.append(log("INFO","=====> Header "+headers));
                        logs.append(log("INFO","=====> Cookie "+cookies));
                        response = webClientUtil.postByJson(url, body, headersKv, cookiesKv);
                    } else {
                        logs.append(log("ERROR","暂不支持该POST请求格式"));
                        logger.error("暂不支持该POST请求格式");
                    }
                    break;
                case "put":
                    logs.append(log("INFO","=====> PUT "+url));
                    logs.append(log("INFO","=====> Body "+body));
                    logs.append(log("INFO","=====> Header "+headers));
                    logs.append(log("INFO","=====> Cookie "+cookies));
                    response = webClientUtil.put(url, body, headersKv, cookiesKv);
                    break;
                case "delete":
                    logs.append(log("INFO","=====> DELETE "+url));
                    logs.append(log("INFO","=====> Header "+headers));
                    logs.append(log("INFO","=====> Cookie "+cookies));
                    response = webClientUtil.delete(url, headersKv, cookiesKv);
                    break;
                default:
                    logs.append(log("ERROR","暂不支持该请求方式"));
                    logger.error("暂不支持该请求方式");
            }
            // 处理响应体，转换为JSON字符串
            String responseBody = "";
            String responseHeaders = "";
            String responseCookies = "";
            try {
                responseBody = new JSONObject(response.bodyToMono(Map.class).block()).toString();
            } catch (Exception e){
                logs.append(log("INFO","响应体JSON转换失败,请确认响应结构是否为JSON!默认直接转为字符串"));
                logger.info("响应体JSON转换失败,请确认响应结构是否为JSON!默认直接转为字符串");
                responseBody = response.bodyToMono(String.class).block();
            }
            logs.append(log("INFO","<===== Response Body : " +responseBody));
            logger.info("Response Body : " +responseBody);
            Map<String,Object> rHeaders = new HashMap<>();
            // 处理响应头，转换为JSON字符串
            for (Map.Entry<String, List<String>> entry:response.headers().asHttpHeaders().entrySet()){
                rHeaders.put(entry.getKey(),entry.getValue());
            }
            responseHeaders = new JSONObject(rHeaders).toString();
            logs.append(log("INFO","<===== Response Header : "+responseHeaders));
            logger.info("Response Header : "+responseHeaders);
            // 处理响应Cookie,转换为JSON字符串
            Map<String,Object> rCookies = new HashMap<>();
            for (Map.Entry<String, List<ResponseCookie>> entry:response.cookies().entrySet()){
                rCookies.put(entry.getKey(),entry.getValue());
            }
            responseCookies = new JSONObject(rCookies).toString();
            logs.append(log("INFO","<===== Response Cookie : "+ responseCookies));
            logger.info("Response Cookie : "+ responseCookies);

            // 处理关联参数
            for (SavesResponse save:saves){
                // 拼接uuid生成唯一key
                String key = save.getParamKey();
                String paramKey = save.getParamKey()+":"+uuid;
                String jsonpath = save.getJexpression();
                String regex = save.getRegex();
                String saveFrom = save.getSaveFrom();
                String saveType = save.getSaveType();
                if ("1".equals(saveFrom)){
                    if ("1".equals(saveType)){
                        String value = String.valueOf(JSONPath.read(responseBody,jsonpath));
                        redisUtil.set(paramKey,value);
                        logs.append(log("INFO",String.format("储存关联参数到redis=> %s:%s",key,value)));
                        logger.info(String.format("储存关联参数到redis=> %s:%s",key,value));
                    }else {
                        Pattern pattern = Pattern.compile(regex);
                        Matcher matcher = pattern.matcher(responseBody);
                        String value = "";
                        if(matcher.find()) {
                            value = matcher.group(0);
                        }
                        logs.append(log("INFO",String.format("储存关联参数到redis=> %s:%s",key,value)));
                        logger.info(String.format("储存关联参数到redis=> %s:%s",key,value));
                        // 往redis存值，设置默认过期时间为一小时
                        redisUtil.set(paramKey,value,3600);
                    }
                }else if ("2".equals(saveFrom)){
                    if ("1".equals(saveType)){
                        String value = String.valueOf(JSONPath.read(responseHeaders,jsonpath));
                        redisUtil.set(paramKey,value);
                        logs.append(log("INFO",String.format("储存关联参数到redis=> %s:%s",key,value)));
                        logger.info(String.format("储存关联参数到redis=> %s:%s",key,value));
                    }else {
                        Pattern pattern = Pattern.compile(regex);
                        Matcher matcher = pattern.matcher(responseHeaders);
                        String value = "";
                        if(matcher.find()) {
                            value = matcher.group(0);
                        }
                        logs.append(log("INFO",String.format("储存关联参数到redis=> %s:%s",key,value)));
                        logger.info(String.format("储存关联参数到redis=> %s:%s",key,value));
                        // 往redis存值，设置默认过期时间为一小时
                        redisUtil.set(paramKey,value,3600);
                    }
                }else if ("3".equals(saveFrom)){
                    if ("1".equals(saveType)){
                        String value = String.valueOf(JSONPath.read(responseCookies,jsonpath));
                        redisUtil.set(paramKey,value);
                        logs.append(log("INFO",String.format("储存关联参数到redis=> %s:%s",key,value)));
                        logger.info(String.format("储存关联参数到redis=> %s:%s",key,value));
                    }else {
                        Pattern pattern = Pattern.compile(regex);
                        Matcher matcher = pattern.matcher(responseCookies);
                        String value = "";
                        if(matcher.find()) {
                            value = matcher.group(0);
                        }
                        logs.append(log("INFO",String.format("储存关联参数到redis=> %s:%s",key,value)));
                        logger.info(String.format("储存关联参数到redis=> %s:%s",key,value));
                        // 往redis存值，设置默认过期时间为一小时
                        redisUtil.set(paramKey,value,3600);
                    }
                }else{
                    logs.append(log("ERROR","不支持从该响应类型取值"));
                    logger.error("不支持从该响应类型取值");
                }
            }
            // 设置默认总体断言结果为真
            boolean verifyResult = true;
            // 遍历断言集合，进行断言
            for (VerifyResponse verify:verifys){
                logs.append(log("INFO","正在进行第"+(verifys.indexOf(verify)+1)+"次断言..."));
                logger.info("正在进行第"+(verifys.indexOf(verify)+1)+"次断言...");
                String verifyType = verify.getVerifyType();
                String expect = verify.getExpect();
                String jsonpath = verify.getJexpression();
                String regex = verify.getRexpression();
                String relationShip = verify.getRelationShip();
                // 判断断言类型是 JsonPath 还是 正则
                if ("1".equals(verifyType)){
                    Object value = JSONPath.read(responseBody,jsonpath);
                    if (value == null){
                        logs.append(log("ERROR","JsonPath未匹配到结果，请确认！"));
                        logger.error("JsonPath未匹配到结果，请确认！");
                        verifyResult = false;
                        continue;
                    }
                    try{
                        logs.append(log("INFO",String.format("表达式：%s,预期结果：%s,断言类型:jsonpath",verify.getJexpression(),verify.getExpect())));
                        logger.info(String.format("表达式：%s,预期结果：%s,断言类型:jsonpath",verify.getJexpression(),verify.getExpect()));
                        superAssert(relationShip,(String)value,expect);
                        logs.append(log("INFO","断言成功！"));
                        logger.info("断言成功！");
                    }catch (AssertionError e){
                        logs.append(log("ERROR","断言失败："+e));
                        logger.error("断言失败："+e);
                        verifyResult = false;
                    }
                }else if ("2".equals(verifyType)){
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(responseBody);
                    String value = "";
                    if(matcher.find()) {
                        value = matcher.group(0);
                    }else {
                        logs.append(log("ERROR","正则表达式未匹配到任何值,请确认！"));
                        logger.error("正则表达式未匹配到任何值,请确认！");
                        verifyResult = false;
                        continue;
                    }
                    try{
                        logs.append(log("INFO",String.format("表达式：%s,预期结果：%s,断言类型:regex",verify.getRexpression(),verify.getExpect())));
                        logger.info(String.format("表达式：%s,预期结果：%s,断言类型:regex",verify.getRexpression(),verify.getExpect()));
                        superAssert(relationShip,value,expect);
                        logs.append(log("INFO","断言成功！"));
                        logger.info("断言成功！");
                    }catch (AssertionError e){
                        logs.append(log("ERROR","断言失败："+e));
                        logger.error("断言失败："+e);
                        verifyResult = false;
                    }
                }else {
                    logs.append(log("ERROR","不支持该断言方式"));
                    logger.error("不支持该断言方式");
                }
            }
            if (jsonAssert!=null && !"".equals(jsonAssert)){
                // false代表非严格校验，只比较部分字段
                logs.append(log("INFO","正在进行json断言..."));
                logger.info("正在进行json断言...");
                try{
                    JSONAssert.assertEquals(jsonAssert,responseBody,false);
                    logs.append(log("INFO","JSON断言成功！"));
                    logger.info("JSON断言成功！");
                }catch (AssertionError e){
                    logs.append(log("ERROR","JSON断言失败："+e));
                    logger.error("JSON断言失败："+e);
                    verifyResult = false;
                }
            }
            //格式化小数
            DecimalFormat df = new DecimalFormat("0.00");
            // 计算执行进度百分比
            String executedRate = df.format(((float)index/casesNum)*100);
            // 计算成功率
            String successRate = df.format(((float)(index-error)/casesNum)*100);
            TestResult testResult = new TestResult(index,index-error,error,casesNum,successRate,executedRate);
            String result = JSON.toJSONString(testResult);
            WebSocketServer.sendInfo(result,"123");

            // 最后生成全局cookie和header
            String gCookies = getCommonParam(testCase.getGlobalCookies(),uuid);
            String gHeaders = getCommonParam(testCase.getGlobalHeaders(),uuid);
            // 判断是否有全局Cookie或者全局Header，如果有则重新生成webclient实例
            if (gCookies != null &&  !"".equals(gCookies)){
                logs.append(log("INFO","Set Golbal Cookie : "+gCookies));
                logs.append(log("INFO","Set Golbal Header : "+gHeaders));
                Map<String,String> maps = JSON.parseObject(gCookies,new TypeReference<Map<String, String>>(){});
                for (Map.Entry<String,String> kvs : maps.entrySet()){
                    String key = getCommonParam(kvs.getKey(),uuid);
                    String value = getCommonParam(kvs.getValue(),uuid);
                    globalCookies.put(key,value);
                }
                webClientUtil = new WebClientUtil(env.getHost(),env.getPort().toString(),globalHeaders,globalCookies);
            }
            if (gHeaders != null &&  !"".equals(gHeaders)){
                Map<String,String> maps = JSON.parseObject(gHeaders,new TypeReference<Map<String, String>>(){});
                for (Map.Entry<String,String> kvs : maps.entrySet()){
                    String key = getCommonParam(kvs.getKey(),uuid);
                    String value = getCommonParam(kvs.getValue(),uuid);
                    globalHeaders.put(key,value);
                }
                webClientUtil = new WebClientUtil(env.getHost(),env.getPort().toString(),globalHeaders,globalCookies);
            }
            if (verifyResult){
                success++;
                executedRow = JSON.toJSONString(new ExecutedRow(index,caseName,1,logs.toString()));
            }else {
                error++;
                executedRow = JSON.toJSONString(new ExecutedRow(index,caseName,0,logs.toString()));
            }
            WebSocketServer.sendInfo(executedRow,"101");
        }
        return null;
    }

//    /**
//     * 正则匹配，保存参数到redis（默认取到第一个匹配项）
//     * @param target
//     * @param regex
//     */
//    private void saveRegexParamToRedis(String target, String key,String uuid, String regex){
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(target);
//        String value = "";
//        if(matcher.find()) {
//            value = matcher.group(0);
//        }
//        logger.info(String.format("储存关联参数到redis=> %s:%s",key,value));
//        // 往redis存值，设置默认过期时间为一小时
//        redisUtil.set(key+":"+uuid,value,3600);
//    }

    /**
     * 超级断言
     * @param type  断言方式
     * @param target  目标字符串
     * @param expect  期待字符串
     * @throws Exception
     */
    private void superAssert(String type,String expect,String target) throws Exception {
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
     * @param uuid 唯一标识
     * @return  替换${key}后的字符串参数
     */
    private String getCommonParam(String param,String uuid) {
        if (StringUtil.isEmptyOrNull(param)) {
            return "";
        }
        // 取公共参数正则
        Matcher m = replaceParamPattern.matcher(param);
        while (m.find()) {
            String replaceKey = m.group(1);
            String value;
            // 从redis中获取值
            value = (String)redisUtil.get(replaceKey+":"+uuid);
            // 如果redis中未能找到对应的值，该用例失败。
            if (value==null){
                logger.error("从redis中未能查询到相关参数,请确认！");
            }else{
                param = param.replace(m.group(), value);
            }
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
