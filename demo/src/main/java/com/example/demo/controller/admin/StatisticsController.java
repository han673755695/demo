package com.example.demo.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 统计控制类
 * 
 * @author my
 * @param <E>
 *
 */
@Controller
@RequestMapping("/admin/statistics")
public class StatisticsController<E> {

	private static final Logger logger = LoggerFactory.getLogger(StatisticsController.class);

	/**
	 * 统计页面
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/toStatistics")
	public String statistics(Model model, HttpServletRequest request) {
		logger.info("进入后台管理统计界面");
		return "/platform/statistics/statistics";
	}

	/**
	  * 进入上传图片页面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/toUpload")
	public String toUpload(Model model, HttpServletRequest request) {
		logger.info("进入后台管理上传图片界面");
		return "/platform/statistics/fileUpload";
	}

}
