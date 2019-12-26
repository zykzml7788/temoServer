package com.creams.temo.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * 注入druid为了yml文件配置生效
 */
@Configuration
public class DruidConfig {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druid(){
        return new DruidDataSource();
    }

    //配置servlet
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("loginUsername","admin");
        hashMap.put("loginPassword","123456");
        //允许访问所有
        hashMap.put("allow","");
        bean.setInitParameters(hashMap);
        return bean;
    }

    //配置Filter

    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean<>(new WebStatFilter());
        HashMap<Object, Object> hashMap = new HashMap<>();
        //排除js,css
        hashMap.put("exclusions", "*.js,*css,/druid/*");
        bean.setInitParameters(hashMap);
        return bean;
    }
}
