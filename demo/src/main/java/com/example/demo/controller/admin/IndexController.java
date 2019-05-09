package com.example.demo.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 后台管理首页控制类
 * @author my
 *
 */
@Controller
@RequestMapping("/admin/index")
public class IndexController {

	private final Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	/**
	 * 进入首页
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/toIndex")
	public String toIndex(Model model, HttpServletRequest request) {
		logger.info("进入后台管理首页");
		return "/platform/index";
	}
	
	/**
	 * 欢迎页面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/toWelcome")
	public String toWelcome(Model model, HttpServletRequest request) {
		logger.info("进入后台管理欢迎页面");
		return "/platform/welcome";
	}
	
}
