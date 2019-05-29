package com.example.demo.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 全局错误页请求跳转路径
 * @author my
 *
 */
@Controller
public class ErrorPageController {
	@RequestMapping(value = "/error400Page")
	public String error400Page() {
		return "error404Page";
	}

	@RequestMapping(value = "/error401Page")
	public String error401Page() {
		return "error404Page";
	}

	@RequestMapping(value = "/error404Page")
	public String error404Page(Model model) {
		model.addAttribute("code", "6666666");
		model.addAttribute("msg", "服务器降级中......");
		return "error404Page";
	}

	@RequestMapping(value = "/error500Page")
	public String error500Page(Model model) {
		return "error404Page";
	}

	@RequestMapping(value = "/errorPage")
	public String error(Model model) {
		return "errorPage";
	}
	
	@RequestMapping(value = "/unauthorized")
	public String unauthorized(Model model, HttpServletRequest request) {
		model.addAttribute("url", request.getRequestURI());
		model.addAttribute("exception", "没有权限访问,请联系管理员");
		return "errorPage";
	}

}
