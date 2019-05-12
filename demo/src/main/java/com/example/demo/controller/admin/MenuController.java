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

import com.alibaba.druid.util.StringUtils;
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
@RequestMapping("/admin/menu")
public class MenuController {

	@Autowired
	private IMenuService menuService;
	
	private final Logger logger = LoggerFactory.getLogger(MenuController.class);
	
	/**
	  * 菜单列表返回页面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/menuList")
	public String menuList(Model model, HttpServletRequest request) {
		logger.info("获取菜单list");
		ResultData menuListJson = menuListJson(model, request);
		model.addAttribute(ResultData.DATAKEY, menuListJson);
		return "/platform/menu/menuList";
	}
	
	
	/**
	  * 菜单列表返回json数据
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/menuList/json")
	@ResponseBody
	public ResultData menuListJson(Model model, HttpServletRequest request) {
		ResultData success = ResultData.getSuccess();
		try {
			logger.info("获取菜单list");
			Menu menu = new Menu();
			menu.setId("123456");
			List<Menu> list = menuService.selectListByMenu(menu);
			success.setData(list);
		} catch (Exception e) {
			success.setStatus(ResultData.ERROR);
			success.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return success;
	}
	
	
	@RequestMapping("/toSaveMenu")
	public String toSaveMenu(Model model, HttpServletRequest request, Menu menu) {
		logger.info("添加菜单页面");
		logger.info(menu.toString());
		return "/platform/menu/menuAdd";
	}
	
	
	/**
	  * 保存菜单
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveMenu")
	@ResponseBody
	public ResultData saveMenu(Model model, HttpServletRequest request, Menu menu) {
		ResultData success = ResultData.getSuccess();
		try {
			logger.info("保存菜单");
			menu.setId(UUIDUtils.getUUID());
			if (StringUtils.isEmpty(menu.getParentId())) {
				menu.setParentId("-1");
			}else {
				Menu parentMenu = menuService.selectByPrimaryKey(menu.getParentId());
				parentMenu.setIsParent("2");
				menuService.updateActiveByMenu(parentMenu);
			}
			menu.setCreateDate(new Date());
			menu.setUpdateDate(new Date());
			logger.info(menu.toString());
			int insertActive = menuService.insertActive(menu);
			logger.info(String.valueOf(insertActive));
			success.setData(insertActive);
		} catch (Exception e) {
			success.setStatus(ResultData.ERROR);
			success.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return success;
	}
	
}
