package com.example.renyibang.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        /*
         * anon： 无需认证即可访问
         * authc： 必须认证才能访问
         * user: 必须拥有 记住我 功能才能用
         * perms: 拥有对某个资源的权限才能访问
         * role: 拥有某个角色权限才能访问
         * */

        // Shiro内置过滤器，可以实现权限相关的拦截器
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/user/login", "anon"); // 登录接口不需要认证
        filterMap.put("/user/add", "authc");
        filterMap.put("/user/update", "authc");
        filterMap.put("/help/test1", "authc,perms[normal]");
        filterMap.put("/help/test2", "authc,perms[waiter]");
        filterMap.put("/help/test3", "authc,perms[admin]");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 关联UserRealm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }
}


