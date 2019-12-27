package com.creams.temo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@MapperScan("com.creams.temo.mapper")
@EnableAsync
public class TemoApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(TemoApplication.class, args);

    }


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        // 解决服务端tomcat 404问题
        return application.sources(TemoApplication.class);
    }
}
