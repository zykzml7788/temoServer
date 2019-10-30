package com.creams.temo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.creams.temo.entity.project.Env;
import com.creams.temo.entity.project.Project;
import com.creams.temo.entity.project.response.EnvResponse;
import com.creams.temo.mapper.project.EnvMapper;
import com.creams.temo.mapper.project.ProjectMapper;
import com.creams.temo.service.project.ProjectService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.rmi.MarshalledObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TemoApplicationTests {


    private WebClient webClient = WebClient.builder()
            .baseUrl("http://129.204.148.24:8888/apis")
            .defaultHeader(HttpHeaders.USER_AGENT,"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko)")
            .build();

    @Test
    public void testWebClient(){
        Mono<Map> response = webClient.get().uri("/project/1?filter={filter}","").retrieve()
                .bodyToMono(Map.class);
        System.out.println(response.block());
    }
}
