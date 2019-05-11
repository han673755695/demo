package com.example.demo.controller.admin;

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

import com.example.demo.common.ResultData;
import com.example.demo.common.TableResultData;
import com.example.demo.domain.Menu;
import com.example.demo.service.IMenuService;
import com.example.demo.service.IUserService;
import com.example.demo.utils.UUIDUtils;

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
	@Autowired
	private IMenuService menuService;
	
	private final Logger logger = LoggerFactory.getLogger(MenuController.class);
	
	@RequestMapping("/menuList")
	public String menuList(Model model, HttpServletRequest request) {
		logger.info("获取菜单list");
		return "/platform/menu/menuList";
	}
	
	@RequestMapping("/menuList/json")
	@ResponseBody
	public ResultData menuListJson(Model model, HttpServletRequest request) {
		ResultData success = ResultData.getSuccess();
		logger.info("获取菜单list");
		Menu menu = new Menu();
		menu.setId("123456");
		List<Menu> list = menuService.selectListByMenu(menu);
		logger.info(list.toString());
		success.setData(list);
		return success;
	}
	
	@RequestMapping("/userList")
	@ResponseBody
	public TableResultData getUserList() {
		List<Map<String,Object>> user = userService.getUser();
		TableResultData tableResultData = new TableResultData();
		tableResultData.setData(user);
		return tableResultData;
	}
	
	@RequestMapping("/saveMenu")
	@ResponseBody
	public ResultData saveMenu(Model model, HttpServletRequest request) {
		ResultData success = ResultData.getSuccess();
		logger.info("获取菜单list");
		Menu menu = new Menu();
		menu.setId(UUIDUtils.getUUID());
		menu.setParentId("-1");
		menu.setCreateDate(new Date());
		menu.setUpdateDate(new Date());
		menu.setName("会员管理");
		menu.setSort("1");
		menu.setStatus("1");
		menu.setUrl("");
		menu.setIsParent("1");
		
		int insertActive = menuService.insertActive(menu);
		logger.info(String.valueOf(insertActive));
		success.setData(insertActive);
		return success;
	}
	
}
