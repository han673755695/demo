package com.example.demo.config;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.utils.AdminUrlFilter;
import com.jagregory.shiro.freemarker.ShiroTags;

@Configuration
public class ShiroConfig {
	// 将自己的验证方式加入容器
	@Bean
	public MyShiroRealm myShiroRealm() {
		MyShiroRealm myShiroRealm = new MyShiroRealm();
		return myShiroRealm;
	}

	// 权限管理，配置主要是Realm的管理认证
	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		MyShiroRealm myShiroRealm = myShiroRealm();
		myShiroRealm.setCachingEnabled(true);
		myShiroRealm.setAuthenticationCachingEnabled(true);
		myShiroRealm.setAuthorizationCachingEnabled(true);
		securityManager.setRealm(myShiroRealm);
		securityManager.setSessionManager(sessionManager());
		securityManager.setCacheManager(cacheManager());
		return securityManager;
	}

	// shiro在页面中使用标签
	@Bean
	public freemarker.template.Configuration setConfiguration(freemarker.template.Configuration cong) {
		cong.setSharedVariable("shiro", new ShiroTags());
		return cong;
	}

	// Filter工厂，设置对应的过滤条件和跳转条件
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		// 自定义过滤器
		Map<String, Filter> filterMap = shiroFilterFactoryBean.getFilters();
		filterMap.put("adminUrlFilter", new AdminUrlFilter());
		shiroFilterFactoryBean.setFilters(filterMap);

		Map<String, String> map = new LinkedHashMap<String, String>();
		// 退出
		//map.put("/admin/login/logout", "logout");
		// 静态资源
		map.put("/platform/**", "anon");
		// 登陆接口
		map.put("/admin/login/login", "anon");
		// 自定义的拦截器 拦截
		// map.put("/admin/menu/**", "adminUrlFilter");
		// map.put("/admin/role/**", "adminUrlFilter");
		map.put("/admin/**", "authc");
		System.out.println("map: " + map);
		// 登录页面
		shiroFilterFactoryBean.setLoginUrl("/admin/login/toLogin");
		// 首页
		shiroFilterFactoryBean.setSuccessUrl("/admin/index/toIndex");
		// 错误页面，认证不通过跳转
		shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
		return shiroFilterFactoryBean;
	}

	// 加入注解的使用，不加入这个注解不生效
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	@Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

	
	/**
     * 配置shiro redisManager 使用的是shiro-redis开源插件
     *
     * @return
     */
    @Bean
    public RedisManager redisManager() {
    	RedisManager redisManager = new RedisManager();
    	redisManager.setHost("192.168.11.132");
        return redisManager;
    }

    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        RedisSessionDAO redisSessionDao = new RedisSessionDAO();
        redisSessionDao.setRedisManager(redisManager());
        sessionManager.setSessionDAO(redisSessionDao);
        return sessionManager;
    }

    /**
     * cacheManager 缓存 redis实现 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }
    
    
    /**
	  * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
	  * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)即可实现此功能
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }

}
