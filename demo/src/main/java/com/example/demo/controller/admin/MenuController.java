package com.example.demo.controller.admin;

import java.util.HashMap;
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

import com.example.demo.common.ResultData;
import com.example.demo.common.TableResultData;
import com.example.demo.service.IUserService;

/**
 * 菜单管理控制类
 * @author my
 *
 */
@Controller
@RequestMapping("/admin/consumer")
public class MenuController {

	@Autowired
	private IUserService userService;
	
	private final Logger logger = LoggerFactory.getLogger(MenuController.class);
	
	@RequestMapping("/menuList")
	public String menuList(Model model, HttpServletRequest request) {
		logger.info("获取菜单list");
		return "/platform/menu/menuList";
	}
	
	@RequestMapping("/userList")
	@ResponseBody
	public TableResultData getUserList() {
		List<Map<String,Object>> user = userService.getUser();
		TableResultData tableResultData = new TableResultData();
		tableResultData.setData(user);
		return tableResultData;
	}
	
}
