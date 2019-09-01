package com.example.demo.config;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.shiro.SystemLogoutFilter;
import com.example.demo.shiro.SystemPermissionsAuthorizationFilter;
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
		myShiroRealm.setAuthorizationCachingEnabled(false);
		securityManager.setRealm(myShiroRealm);
		securityManager.setSessionManager(sessionManager());
		securityManager.setCacheManager(shiroCacheManager());
		return securityManager;
	}

	// shiro在页面中使用标签
	@Bean("configuration")
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
		filterMap.put("systemPermissions", new SystemPermissionsAuthorizationFilter());
		filterMap.put("systemLogout", new SystemLogoutFilter());
		shiroFilterFactoryBean.setFilters(filterMap);

		Map<String, String> map = new LinkedHashMap<String, String>();
		// 退出
		map.put("/admin/login/logout", "systemLogout");
		// 静态资源
		map.put("/platform/**", "anon");
		// 登陆接口
		map.put("/admin/login/login", "anon");
		map.put("/admin/login/toLogin", "anon");
		map.put("/admin/index/toIndex", "anon");
		map.put("/unauthorized", "anon");
		// 自定义的拦截器 拦截
		map.put("/admin/menu/**", "systemPermissions");
		map.put("/admin/role/**", "systemPermissions");
		map.put("/**", "authc");
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
	
	/**
	 * 	单机session
	 * 
	 * @return
	 */
	@Bean("shiroCacheManager")
	public CacheManager shiroCacheManager() {
		MemoryConstrainedCacheManager cacheManager = new MemoryConstrainedCacheManager();
		return cacheManager;
	}

	
	/**
     * 	配置shiro 
     *
     * @return
     */
    @Bean
    public SessionManager sessionManager() {
    	DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		// URL重写中去掉jsessionId
		sessionManager.setSessionIdUrlRewritingEnabled(false);
		// 超时时间
		sessionManager.setGlobalSessionTimeout(1800000L);
		// 定时检查失效的session,默认true
		sessionManager.setSessionValidationSchedulerEnabled(true);
		// 删除过期的session,默认true
		sessionManager.setDeleteInvalidSessions(true);
		// 相隔多久检查一次session的有效性,使用默认的60分钟
		// sessionManager.setSessionValidationInterval(cacheTimeOut);
		// session存储的实现
		sessionManager.setSessionDAO(shiroSessionDAO());
		// sessionIdCookie的实现,用于重写覆盖容器默认的JSESSIONID
		sessionManager.setSessionIdCookie(shiroSessionIdCookie());
        return sessionManager;
    }
    
    
    /**
	 * sessionIdCookie的实现,用于重写覆盖容器默认的JSESSIONID
	 * 
	 * @return
	 */
    @Bean()
	public SimpleCookie shiroSessionIdCookie() {
		SimpleCookie sessionIdCookie = new SimpleCookie();
		// cookie的name,对应的默认是 JSESSIONID
		sessionIdCookie.setName("SHAREJSESSIONID");
		// more secure, protects against XSS attacks
		sessionIdCookie.setHttpOnly(true);
		// jsessionId的path为 / 用于多个系统共享jsessionId
		sessionIdCookie.setPath("/");

		return sessionIdCookie;
	}
    
    
    /**
	 * session存储的实现
	 * 
	 * @return
	 */
	@Bean()
	public SessionDAO shiroSessionDAO() {
		EnterpriseCacheSessionDAO sessionDAO = new EnterpriseCacheSessionDAO();
		return sessionDAO;
	}
    

    /**
     * shiro生命周期
     * @return
     */
    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
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
