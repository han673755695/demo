package com.example.demo.config;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.utils.AdminUrlFilter;
import com.jagregory.shiro.freemarker.ShiroTags;

import net.sf.ehcache.CacheManager;

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
	public SecurityManager securityManager(EhCacheManager ehCacheManager) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		MyShiroRealm myShiroRealm = myShiroRealm();
		myShiroRealm.setCachingEnabled(true);
		myShiroRealm.setAuthenticationCachingEnabled(true);
		myShiroRealm.setAuthorizationCachingEnabled(true);
		securityManager.setRealm(myShiroRealm);
		securityManager.setCacheManager(ehCacheManager);
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

	/**
	 * 缓存管理器
	 * 
	 * @return
	 */
	@Bean
	public EhCacheManager ehCacheManager() {
		EhCacheManager em = new EhCacheManager();
		em.setCacheManagerConfigFile("classpath:ehcache.xml");
		return em;
	}
	
	@Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


}
