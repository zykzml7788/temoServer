package com.creams.temo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.creams.temo.mapper")
public class TemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TemoApplication.class, args);
    }

}
