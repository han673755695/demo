package com.example.demo.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;

public class SystemLogoutFilter extends LogoutFilter {
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		Subject subject = getSubject(request, response);
		String redirectUrl = getRedirectUrl(request, response, subject);
		
		subject.logout();
		
		issueRedirect(request, response, redirectUrl);
		
		return false;
	}
}
