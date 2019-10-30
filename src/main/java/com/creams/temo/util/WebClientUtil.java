package com.creams.temo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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

    private WebClientUtil(String baseUrl,Map<String,String> headers,Map<String,String> cookies){
        // 函数式编程，遍历请求头构造参数
        Consumer<HttpHeaders> headersConsumer = null;
        Consumer<MultiValueMap<String, String>> cookiesConsumer = null;
        if (headers != null){
            headersConsumer = n->{
                for (Map.Entry<String,String> entry:headers.entrySet()){
                    n.add(entry.getKey(),entry.getValue());
                }
            };
            headersConsumer.accept(new HttpHeaders());
        }
        if (cookies != null){
            cookiesConsumer = n->{
                for (Map.Entry<String,String> entry:cookies.entrySet()){
                    n.add(entry.getKey(), entry.getValue());
                }
            };
            cookiesConsumer.accept(new LinkedMultiValueMap<>());
        }
        if (headersConsumer != null && cookiesConsumer != null){
            webClient = WebClient.builder()
                    .baseUrl(baseUrl)
                    .defaultHeaders(headersConsumer)
                    .defaultCookies(cookiesConsumer)
                    .build();
        }else if (headersConsumer != null){
            webClient = WebClient.builder()
                    .baseUrl(baseUrl)
                    .defaultHeaders(headersConsumer)
                    .build();
        }else if (cookiesConsumer != null) {
            webClient = WebClient.builder()
                    .baseUrl(baseUrl)
                    .defaultCookies(cookiesConsumer)
                    .build();
        }else {
            webClient = WebClient.builder()
                    .baseUrl(baseUrl)
                    .build();
        }

    }

    /**
     * get请求
     * @param url 请求地址
     * @return
     */
    private Map get(String url){
        Mono<Map> response = webClient.get().uri(url).retrieve()
                .onStatus(HttpStatus::isError, res -> Mono.error(new RuntimeException(res.statusCode().value() + ":" + res.statusCode().getReasonPhrase())))
                .bodyToMono(Map.class).timeout(Duration.of(10, ChronoUnit.SECONDS))
                .doAfterSuccessOrError((obj, ex) -> {
                    logger.info("请求方式：GET,请求地址："+url+",响应结果为:\n"+obj);
                    if(ex != null){
                        logger.error("请求方式：GET,请求地址："+url+",响应异常为:"+ex.getMessage());
                    }
                });
        return response.block();
    }

    /**
     * post 表单请求
     * @param url 请求地址
     * @param formData  表单参数
     * @return
     */
    private Map postByFormData(String url, MultiValueMap<String,String> formData){
        Mono<Map> response = webClient.post().uri(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(Map.class).timeout(Duration.of(10, ChronoUnit.SECONDS))
                .doAfterSuccessOrError((obj, ex) -> {
                    logger.info("请求方式：POST 表单,请求地址："+url+",请求表单:\n"+formData.toString()+",响应结果为:\n"+obj);
                    if(ex != null){
                        logger.error("请求方式：POST 表单,请求地址："+url+",请求表单:\n"+formData.toString()+",响应异常为:"+ex.getMessage());
                    }
                });;
        return response.block();
    }

    /**
     *  post Json请求
     * @param url 请求地址
     * @param json  json字符串
     * @return
     */
    private Map postByJson(String url, String json){
        Mono<Map> response = webClient.post().uri(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(json))
                .retrieve()
                .bodyToMono(Map.class).timeout(Duration.of(10, ChronoUnit.SECONDS))
                .doAfterSuccessOrError((obj, ex) -> {
                    logger.info("请求方式：POST JSON,请求地址："+url+",请求体:\n"+json.toString()+",响应结果为:\n"+obj);
                    if(ex != null){
                        logger.error("请求方式：POST JSON,请求地址："+url+",请求体:\n"+json.toString()+",响应异常为:"+ex.getMessage());
                    }
                });;
        return response.block();
    }

    public static void main(String[] args) {
        WebClientUtil webClientUtil = new WebClientUtil("http://129.204.148.24:8080/temo",null,null);
//        System.out.println(webClientUtil.get("/project/1?filter="));
        System.out.println(webClientUtil.postByJson("/project/", "{\n" +
                "\t\"envs\": [],\n" +
                "\t\"pname\": \"测试webClient\"\n" +
                "}"));
    }
}
