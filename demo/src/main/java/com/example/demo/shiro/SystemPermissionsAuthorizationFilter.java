package com.example.demo.shiro;

import java.io.IOException;


import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;

/**
 * 
* 描述：   
* 创建人：HYD
* 创建时间：2019年9月1日 下午10:39:19   
* 修改人：HYD
* 修改时间：2019年9月1日 下午10:39:19
* 修改备注：   后台统一的权限判断类
* @version
 */
public class SystemPermissionsAuthorizationFilter extends PermissionsAuthorizationFilter {
	@Override
	public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws IOException {
		Subject subject = this.getSubject(request, response);
		
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		String uri = servletRequest.getRequestURI();
		
		boolean permitted = subject.isPermitted(uri);

		return permitted;
	}
	
}
