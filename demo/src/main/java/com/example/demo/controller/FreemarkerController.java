package com.example.demo.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.common.Page;
import com.example.demo.common.ResultData;
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
		List<Map<String, Object>> user = userService.getUser();
		logger.info(user.toString());
		return "/platform/index";
	}
	
	@ResponseBody
	@RequestMapping("/two")
	public ResultData two(Model model, HttpServletRequest request) {
		ResultData success = ResultData.getSuccess();
		Page page = Page.getPage(request);
		List<Map<String, Object>> user = userService.getUser();
		success.setData(user);
		success.setPage(page);
		System.out.println("123");
		return success;
	}
	
}
