package com.zhoushan.wenhua.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /**
     * 创建ShiroFilterFactoryBean
     */
    @Bean
   public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Autowired DefaultWebSecurityManager securityManager){
       ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
      //添加安全管理器
       shiroFilterFactoryBean.setSecurityManager(securityManager);
       //添加shiro内置过滤器
       /**
        * Shiro内置过滤器
        * anon:无需认证(登录)可以访问
        * authc:必须认证才能访问
        * user:如果使用rememberMe的功能可以直接访问
        * perms:该资源必须得到资源权限才能访问
        * role:该资源必须得到角色权限才能访问
        */
       Map<String,String> filterMap = new LinkedHashMap<>();

        filterMap.put("/*","anon");

        //授权
        shiroFilterFactoryBean.setLoginUrl("/index");
        shiroFilterFactoryBean.setUnauthorizedUrl("/index");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
       return shiroFilterFactoryBean;
   }
    /**
     * 创建DefaultWebecurityManager
     */
    @Bean
      public DefaultWebSecurityManager getDefaultWebSecurityManager(@Autowired UserRealm realm){
          DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
          defaultWebSecurityManager.setRealm(realm);
          return defaultWebSecurityManager;
      }
    /**
     * 创建Realm
     */
    @Bean
    public UserRealm getUserRealm(){
        return new UserRealm();
    }

    /**
     * 创建ShiroDialect
     */
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }

}
