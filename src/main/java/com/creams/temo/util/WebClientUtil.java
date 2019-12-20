package com.creams.temo.util;

import com.alibaba.fastjson.JSONObject;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Consumer;

/**
 *  基于WebClient封装客户端请求工具类
 */
public class WebClientUtil {

    private WebClient webClient;
    private static Logger logger = LoggerFactory.getLogger("fileInfoLog");

    public WebClientUtil(String baseUrl, Map<String, String> headers, Map<String, String> cookies) throws SSLException {
        logger.info(String.format("开始创建webclient实例。。，设置default headers:%s,default cookies:%s",
                headers.toString(),cookies.toString()));
        // 设置SSL
        HttpClient secure = HttpClient.create()
                .secure(t -> t.sslContext(SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE)));

        webClient = WebClient
                .builder()
                .clientConnector(new ReactorClientHttpConnector(secure))
                .baseUrl(baseUrl)
                .defaultHeaders(n->{
                    for (Map.Entry<String,String> entry:headers.entrySet()){
                        n.add(entry.getKey(), entry.getValue());
                    }
                })
                .defaultCookies(n->{
                    for (Map.Entry<String,String> entry:cookies.entrySet()){
                        n.add(entry.getKey(), entry.getValue());
                    }
                })
                .build();

    }

    /**
     * get请求
     * @param url 请求地址
     * @return
     */
    public ClientResponse get(String url,Map<String,String> params,Map<String,String> headers,Map<String,String> cookies){
        logger.info("开始调用接口。。");
        logger.info("========================================");
        logger.info("GET "+url);
        logger.info("Params "+params);
        logger.info("Headers  "+headers);
        logger.info("Cookies "+cookies);

        MultiValueMap<String,String> paramsKv = new LinkedMultiValueMap<>();
        for (Map.Entry<String,String> entry:params.entrySet()){
            paramsKv.add(entry.getKey(),entry.getValue());
        }
        Mono<ClientResponse> mono  = webClient.get()
                .uri(u->u
                        .path(url)
                        .queryParams(paramsKv)
                        .build()
                )
                .headers(n->{
                    for (Map.Entry<String,String> entry: headers.entrySet()){
                        n.add(entry.getKey(),entry.getValue());
                    }
                })
                .cookies(n->{
                    for (Map.Entry<String,String> entry: cookies.entrySet()){
                        n.add(entry.getKey(),entry.getValue());
                    }
                })
                .acceptCharset(StandardCharsets.UTF_8)
                .exchange();

        return mono.block();
    }

    /**
     * post 表单请求
     * @param url 请求地址
     * @param formData  表单参数
     * @return
     */
    public ClientResponse postByFormData(String url, MultiValueMap<String,String> formData,Map<String,String> headers,Map<String,String> cookies){

        logger.info("开始调用接口。。");
        logger.info("========================================");
        logger.info("POST "+url);
        logger.info("FormData "+formData);
        logger.info("Headers  "+headers);
        logger.info("Cookies "+cookies);
        // 函数式编程，遍历请求头构造参数

        Mono<ClientResponse> mono = webClient.post().uri(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .headers(n->{
                    for (Map.Entry<String,String> entry:headers.entrySet()){
                        n.add(entry.getKey(),entry.getValue());
                    }
                })
                .cookies(n-> {
                    for (Map.Entry<String, String> entry : cookies.entrySet()) {
                        n.add(entry.getKey(), entry.getValue());
                    }
                })
                .acceptCharset(StandardCharsets.UTF_8)
                .exchange();
        return mono.block();
    }

    /**
     *  post Json请求
     * @param url 请求地址
     * @param json  json字符串
     * @return
     */
    public ClientResponse postByJson(String url, String json,Map<String,String> headers,Map<String,String> cookies){
        logger.info("开始调用接口。。");
        logger.info("========================================");
        logger.info("POST "+url);
        logger.info("Body "+json);
        logger.info("Headers  "+headers);
        logger.info("Cookies "+cookies);
        // 函数式编程，遍历请求头构造参数
        Mono<ClientResponse> mono = webClient.post().uri(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(json))
                .headers(n->{
                    for (Map.Entry<String,String> entry:headers.entrySet()){
                        n.add(entry.getKey(),entry.getValue());
                    }
                })
                .cookies(n-> {
                    for (Map.Entry<String, String> entry : cookies.entrySet()) {
                        n.add(entry.getKey(), entry.getValue());
                    }
                })
                .acceptCharset(StandardCharsets.UTF_8)
                .exchange();
        return mono.block();
    }

    /**
     *  put请求
     * @param url 请求地址
     * @param json  json字符串
     * @return
     */
    public ClientResponse put(String url, String json,Map<String,String> headers,Map<String,String> cookies){
        logger.info("开始调用接口。。");
        logger.info("========================================");
        logger.info("PUT "+url);
        logger.info("Body "+json);
        logger.info("Headers  "+headers);
        logger.info("Cookies "+cookies);
        // 函数式编程，遍历请求头构造参数
        Mono<ClientResponse> mono = webClient.put().uri(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(json))
                .headers(n->{
                    for (Map.Entry<String,String> entry:headers.entrySet()){
                        n.add(entry.getKey(),entry.getValue());
                    }
                })
                .cookies(n-> {
                    for (Map.Entry<String, String> entry : cookies.entrySet()) {
                        n.add(entry.getKey(), entry.getValue());
                    }
                })
                .acceptCharset(StandardCharsets.UTF_8)
                .exchange();

        return mono.block();
    }

    /**
     *  delete请求
     * @param url 请求地址
     * @return
     */
    public ClientResponse delete(String url,Map<String,String> headers,Map<String,String> cookies){
        logger.info("开始调用接口。。");
        logger.info("========================================");
        logger.info("DELETE "+url);
        logger.info("Headers  "+headers);
        logger.info("Cookies "+cookies);
        // 函数式编程，遍历请求头构造参数
        Mono<ClientResponse> mono = webClient.delete().uri(url)
                .headers(n->{
                    for (Map.Entry<String,String> entry:headers.entrySet()){
                        n.add(entry.getKey(),entry.getValue());
                    }
                })
                .cookies(n-> {
                    for (Map.Entry<String, String> entry : cookies.entrySet()) {
                        n.add(entry.getKey(), entry.getValue());
                    }
                })
                .acceptCharset(StandardCharsets.UTF_8)
                .exchange();

        return mono.block();
    }

    public static void main(String[] args) throws SSLException {
        WebClientUtil webClientUtil = new WebClientUtil("http://129.204.148.24:8080/temo",new HashMap<>(),new HashMap<>());
        Map<String,String> param = new HashMap<>();
        Map<String,String> headers = new HashMap<>();
        param.put("filter","123456");
        System.out.println(new JSONObject(webClientUtil.get("/project/1",param,headers,new HashMap<>()).bodyToMono(Map.class).block()));
       webClientUtil.put("/project/a3c948f2-bd99-4315-8e7c-1c1dd9991a8b", "{\n" +
               "\t\"pid\": \"a3c948f2-bd99-4315-8e7c-1c1dd9991a8b\",\n" +
               "\t\"envs\": [],\n" +
               "\t\"pname\": \"测试webClientAAA\"\n" +
               "}",new HashMap<>(),new HashMap<>());
        webClientUtil.delete("/prject/69cce7db-7b7f-4fbc-b1f8-d0f8e5dea6f4",new HashMap<>(),new HashMap<>());
    }
}
