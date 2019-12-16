/**
 * All rights Reserved, Designed By ZC.LangFang
 * @Title:  ShiroConfiguration.java
 * @author: XueYang.Li
 * @date:   2019/3/1 9:33 AM
 * @version V1.0
 * @Copyright: 2019/3/1 INM Inc. All rights reserved.
 * 注意：本内容仅限于河北志晟信息技术股份有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.zcx.test.config;


import com.zcx.test.common.ApiConst;
import com.zcx.test.common.Consts;
import com.zcx.test.config.security.Authentication;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
* @Title: ShiroConfiguration
* @Package: com.zc.partymember.config
* @Describe: Shiro配置
* @author: XueYang.Li
* @date: 2019/3/1 9:33 AM
* @version: V1.0
**/
@Configuration
public class ShiroConfiguration {
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(
            @Value("${shiro.exceptions}") String shiroException) {

        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager());
        //配置登录的url和登录成功的url
        bean.setLoginUrl("/login");
        //自定义过滤器
        Map<String, Filter> filtersMap = bean.getFilters();
        bean.setFilters(filtersMap);
        //配置访问权限
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 添加例外
        filterChainDefinitionMap.put(ApiConst.STATIC_CSS, Consts.ANON);
        filterChainDefinitionMap.put(ApiConst.STATIC_JS, Consts.ANON);
        filterChainDefinitionMap.put("/**/*.eot", Consts.ANON);
        filterChainDefinitionMap.put("/**/*.svg", Consts.ANON);
        filterChainDefinitionMap.put("/**/*.ttf", Consts.ANON);
        filterChainDefinitionMap.put("/**/*.woff", Consts.ANON);
        if (!StringUtils.isEmpty(shiroException)) {
            String[] shiroExceptions = shiroException.split(",");
            for (String strUrl : shiroExceptions) {
                filterChainDefinitionMap.put(strUrl, Consts.ANON);
            }
        }

        filterChainDefinitionMap.put(ApiConst.ALL_1, Consts.AUTHC);
        filterChainDefinitionMap.put(ApiConst.ALL_2, Consts.AUTHC);
        filterChainDefinitionMap.put(ApiConst.ALL_3, Consts.AUTHC);
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return bean;
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm());
        securityManager.setSessionManager(webSessionManager());
        return securityManager;
    }

    @Bean
    public Realm realm() {
        Authentication authentication = new Authentication();
        authentication.setCredentialsMatcher(credentialsMatcher());
        return authentication;
    }

    @Bean
    public WebSessionManager webSessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        Cookie cookie = new SimpleCookie();
        cookie.setName("question_session");
        // 1小时
        cookie.setMaxAge(60*60);
        sessionManager.setSessionIdCookie(cookie);
        return sessionManager;
    }

    @Bean
    public CredentialsMatcher credentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("MD5");
        matcher.setHashIterations(1024);
        return matcher;
    }
}
