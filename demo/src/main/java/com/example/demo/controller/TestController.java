package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.common.ResultData;
import com.example.demo.domain.Menu;
import com.example.demo.eunm.CommonEunm;
import com.example.demo.service.IMenuService;

@RequestMapping("/testController")
@Controller
public class TestController {

	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	private IMenuService menuService;
	
	@RequestMapping("/menuList")
	@ResponseBody
	public ResultData menuList(HttpServletRequest request,Model model,HttpServletResponse response) {
		ResultData success = ResultData.getSuccess();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("menuType", CommonEunm.menuType.菜单.getValue());
			List<Menu> menuList = menuService.selectMenuByPid(params);
			success.setData(menuList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}
	
}
