package com.example.demo.controller.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.druid.util.StringUtils;
import com.example.demo.common.ResultData;
import com.example.demo.domain.Menu;
import com.example.demo.eunm.RedisKeyEnum;
import com.example.demo.service.IMenuService;
import com.example.demo.utils.RedisUtils;
import com.example.demo.utils.RequestParamUtils;


/**
 * 后台管理首页控制类
 * @author my
 *
 */
@Controller
@RequestMapping("/admin/index")
public class IndexController {

	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	@Autowired
	private IMenuService menuService;
	
	/**
	 * 进入首页
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/toIndex")
	public String toIndex(Model model, HttpServletRequest request) {
		try {
			logger.info("进入后台管理首页");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
