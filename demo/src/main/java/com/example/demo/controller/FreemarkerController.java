package com.example.demo.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.common.Page;
import com.example.demo.common.ResultData;
import com.example.demo.domain.User;
import com.example.demo.service.IUserService;
import com.example.demo.utils.RequestParamUtils;

@Controller
@RequestMapping("/freemarker")
public class FreemarkerController {

	private static final Logger logger = LoggerFactory.getLogger(FreemarkerController.class);
	private String COUPON_KEY = "coupon";

	@Autowired
	private IUserService userService;
	@Autowired
	RedisTemplate redisTemplate;

	@RequestMapping("/index")
	public String index(Model model, HttpServletRequest request) {

		return "/platform/index";
	}

	@ResponseBody
	@RequestMapping("/test")
	public ResultData two(Model model, HttpServletRequest request) {
		ResultData success = ResultData.getSuccess();
		
		for (int j = 0; j < 100; j++) {
			String format = String.format("abc%s", j);
			System.out.println("format: " + format);
		}
//		redisTemplate.opsForSet().add(COUPON_KEY, String.format("abc%s", i));
//		redisTemplate.opsForSet().add(COUPON_KEY, String.format("abc%s", i));
		return success;
	}

}
