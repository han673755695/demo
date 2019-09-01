package com.example.demo.shiro;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;

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
