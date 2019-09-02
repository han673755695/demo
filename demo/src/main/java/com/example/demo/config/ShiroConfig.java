package com.example.demo.config;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.example.demo.shiro.SystemPermissionsAuthorizationFilter;
import com.jagregory.shiro.freemarker.ShiroTags;

/**
 * shiro配置类
  * 描述：   
  * 创建人：HYD
  * 创建时间：2019年9月1日 下午10:42:27   
  * 修改人：HYD
  * 修改时间：2019年9月1日 下午10:42:27
  * 修改备注：   
 * @version
 */
@Configuration
@PropertySource("classpath:application.properties")
public class ShiroConfig {
	
	//	org.apache.shiro.spring.web.config.ShiroFilterChainDefinition
	@Autowired
    RedisSessionDAO redisSessionDAO;
    @Autowired
    RedisCacheManager redisCacheManager;
    @Autowired
    MyShiroRealm myShiroRealm;
    
    /**
     * 	配置shiro	session管理器
     * @return
     */
    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        // inject redisSessionDAO
        sessionManager.setSessionDAO(redisSessionDAO);
        return sessionManager;
    }

    /**
     * 	配置shiro安全管理器
     * @param realms
     * @param sessionManager
     * @return
     */
    @Bean
    public SessionsSecurityManager securityManager(List<Realm> realms, SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		myShiroRealm.setCachingEnabled(true);
		//不缓存认证(会有问题)
		myShiroRealm.setAuthenticationCachingEnabled(false);
		//缓存授权
		myShiroRealm.setAuthorizationCachingEnabled(true);
		securityManager.setRealm(myShiroRealm);
        
        //inject sessionManager
        securityManager.setSessionManager(sessionManager);

        // inject redisCacheManager
        securityManager.setCacheManager(redisCacheManager);
        return securityManager;
    }
    
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        return chainDefinition;
    }
    
	/**
	 * 	配置shiro拦截器工厂
	 * @param securityManager
	 * @return
	 */
    @Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		// 自定义过滤器
		Map<String, Filter> filterMap = shiroFilterFactoryBean.getFilters();
		filterMap.put("systemPermissions", new SystemPermissionsAuthorizationFilter());
		shiroFilterFactoryBean.setFilters(filterMap);

		Map<String, String> map = new LinkedHashMap<String, String>();
		// 退出
		map.put("/admin/login/logout", "logout");
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
	
    
	// shiro在页面中使用标签
	@Bean("configuration")
	public freemarker.template.Configuration setConfiguration(freemarker.template.Configuration cong) {
		cong.setSharedVariable("shiro", new ShiroTags());
		return cong;
	}

}
