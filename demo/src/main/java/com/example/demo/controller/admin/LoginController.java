package com.example.demo.controller.admin;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.example.demo.common.ResultData;
import com.example.demo.domain.User;
import com.example.demo.service.IUserService;
import com.example.demo.utils.RequestParamUtils;

@Controller
@RequestMapping("/admin/login")
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private IUserService userService;
	
	
	@RequestMapping("/toLogin")
	public String toLogin(Model model,HttpServletRequest request) {
		model.addAttribute("name", "张三");
		
		logger.info("进入登陆页面");
		
		return "/platform/login";
	}
	
	
	/**
	 * 登陆方法
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/login")
	@ResponseBody
	public ResultData login(Model model, HttpServletRequest request) {
		ResultData success = ResultData.getSuccess();
		Map<String,Object> parameterMap = RequestParamUtils.getParameterMap(request);
		String username = (String) parameterMap.get("username");
		String password = (String) parameterMap.get("password");
		try {
			if (StringUtils.isEmpty(username)) {
				success.setStatus(ResultData.ERROR);
				success.setMessage("用户名不能为空");
				return success;
			}
			if (StringUtils.isEmpty(password)) {
				success.setStatus(ResultData.ERROR);
				success.setMessage("密码不能为空");
				return success;
			}
			
			// 从SecurityUtils里边创建一个 subject
	        Subject subject = SecurityUtils.getSubject();
	        // 在认证提交前准备 token（令牌）
	        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
	        // 执行认证登陆
	        subject.login(token);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("登陆异常");
		}
		return success;
	}
	
	
}
