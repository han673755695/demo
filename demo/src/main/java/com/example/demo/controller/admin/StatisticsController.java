package com.example.demo.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 统计控制类
 * @author my
 *
 */
@Controller
@RequestMapping("/admin/statistics")
public class StatisticsController {

	private final Logger logger = LoggerFactory.getLogger(StatisticsController.class);
	
	/**
	 * 统计页面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/toStatistics")
	public String statistics(Model model, HttpServletRequest request) {
		logger.info("进入后台管理统计界面");
		return "/platform/statistics/statistics";
	}
	
}
