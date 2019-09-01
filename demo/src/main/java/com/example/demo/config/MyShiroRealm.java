package com.example.demo.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.example.demo.domain.Menu;
import com.example.demo.domain.User;
import com.example.demo.service.IMenuService;
import com.example.demo.service.IUserService;

public class MyShiroRealm extends AuthorizingRealm {

	private static final Logger logger = Logger.getLogger(MyShiroRealm.class);
	
	Boolean cachingEnabled = true;
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IMenuService menuService;

	//授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

		// 因为非正常退出，即没有显式调用 SecurityUtils.getSubject().logout()
		// (可能是关闭浏览器，或超时)，但此时缓存依旧存在(principals)，所以会自己跑到授权方法里。
		if (!SecurityUtils.getSubject().isAuthenticated()) {
			doClearCache(principalCollection);
			SecurityUtils.getSubject().logout();
			return null;
		}
		
		SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
//		Session session = SecurityUtils.getSubject().getSession();
//		User user = (User) session.getAttribute("user");
		
		String userId = (String) principalCollection.getPrimaryPrincipal();
		
		List<Menu> menuList = menuService.selectByUserId(userId);
		for (Menu menu : menuList) {
			if (!StringUtils.isEmpty(menu.getUrl())) {
				simpleAuthorInfo.addStringPermission(menu.getUrl());
			}
		}
		simpleAuthorInfo.addStringPermission("/admin/menu/list");// 给当前用户授权url为hello的权限码
		return simpleAuthorInfo;
	}

	//认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		// 获取基于用户名和密码的令牌
		// 实际上这个authcToken是从LoginController里面currentUser.login(token)传过来的
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		char[] password = token.getPassword();
		String username = token.getUsername();
		Map parameterMap = new HashMap();
		parameterMap.put("username", username);
		parameterMap.put("password", new String(password));
		User user = userService.selectUserByNameAndPwd(parameterMap);
		if (user == null) {
			throw new AuthenticationException("用户名或密码不正确");
		}

		/*
		 * 第一个参数随便放，可以放user对象，程序可在任意位置获取 放入的对象 第二个参数必须放密码， 第三个参数放
		 * 当前realm的名字，因为可能有多个realm
		 */
		AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user.getId(), user.getPassword(), this.getName());

		
		// 清之前的授权信息
		super.clearCachedAuthorizationInfo(authcInfo.getPrincipals());
		SecurityUtils.getSubject().getSession().setAttribute("user", user);
		return authcInfo;
	}

}
