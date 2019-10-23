package com.creams.temo;

import com.github.pagehelper.PageHelper;
import javafx.application.Application;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.omg.PortableInterceptor.Interceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Properties;

@EnableSwagger2
@SpringBootApplication
@MapperScan("com.creams.temo.mapper")
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
