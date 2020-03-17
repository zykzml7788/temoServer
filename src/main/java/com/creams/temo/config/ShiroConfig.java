package com.creams.temo.config;


import com.creams.temo.filter.ShiroUserFilter;
import com.creams.temo.shiro.CustomRealm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    //不加这个注解不生效，具体不详
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }

    //将自己的验证方式加入容器
    //Realm，里面需要自己实现认证和授权业务
    @Bean
    public CustomRealm myShiroRealm() {
        CustomRealm customRealm = new CustomRealm();
        return customRealm;
    }

    //cookie管理
    @Bean
    public SimpleCookie rememberMeCookie() {
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(1 * 60 * 60);
        return cookie;
    }

    //会话管理器
    @Bean
    public SessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionIdUrlRewritingEnabled(true);
        sessionManager.setGlobalSessionTimeout(1 * 60 * 60 * 1000);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionIdCookie(rememberMeCookie());
        return sessionManager;
    }

    //管理shiro的生命周期
    @Bean(name = "lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    //权限管理，配置主要是Realm的管理认证
    //安全管理器
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        return securityManager;
    }

    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 自定义拦截器的配置
        Map<String, Filter> filter = new HashMap<>();
        filter.put("costom", new ShiroUserFilter());
        shiroFilterFactoryBean.setFilters(filter);
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后要跳转的链接
        //shiroFilterFactoryBean.setSuccessUrl("/index");
        //设置无权限跳转页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");
        Map<String,String> map = new LinkedHashMap<>();

        //swagger为静态页面，需要增加这个接口 anon表示不鉴权
        map.put("/swagger/**", "anon");
        map.put("/swagger-ui.html","anon");
        map.put("/swagger-resources/**","anon");
        map.put("/v2/api-docs/**","anon");
        map.put("/webjars/springfox-swagger-ui/**","anon");
        map.put("/login-controller/**", "anon");
        map.put("/**", "costom");
//        shiroFilterFactoryBean.setLoginUrl("/");
        //对PermissionAction.class 中的url进行权限控制
        //map.put("/user", "roles[user]");//需要user角色才可以访问
        //map.put("/user/per", "perms[user:query]");//需要user角色才可以访问
        //map.put("/admin", "roles[admin]");
        //chains.put("/**", "authc");//表示需要认证，才能访问
        //map.put("/**", "user");//表示需要认证或记住我都能访问
        //错误页面，认证不通过跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/error");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    //开启shiro注解权限控制
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
