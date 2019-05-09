package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.domain.Page;
import com.example.demo.domain.ResultData;
import com.example.demo.domain.User;
import com.example.demo.service.IUserService;

@Controller
@RequestMapping("/freemarker")
public class FreemarkerController {

	private final Logger logger = LoggerFactory.getLogger(FreemarkerController.class);
	
	@Autowired
	private IUserService userService;
	
	@RequestMapping("/index")
	public String index(Model model, HttpServletRequest request) {
		User user = userService.getUser();
		logger.info(user.toString());
		return "/platform/index";
	}
	
	@ResponseBody
	@RequestMapping("/two")
	public ResultData two(Model model, HttpServletRequest request) {
		ResultData success = ResultData.getSuccess();
		Page page = Page.getPage(request);
		User student = new User("张三", 23, "playgame");
		success.setData(student);
		success.setPage(page);
		System.out.println("123");
		return success;
	}
	
}
