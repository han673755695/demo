package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.domain.Page;
import com.example.demo.domain.ResultData;
import com.example.demo.domain.Student;

@Controller
@RequestMapping("/freemarker")
public class FreemarkerController {

	@RequestMapping("/index")
	public String index(Model model, HttpServletRequest request) {
		//字符串类型的值
		model.addAttribute("name", "张三");
		//list类型的值
		List list = new ArrayList();
		list.add(0);
		list.add(1);
		list.add(2);
		model.addAttribute("list", list);
		//map类型的值
		Map<String,Object> dataMap = new HashMap();
		dataMap.put("key1", "value1");
		dataMap.put("key2", "value2");
		dataMap.put("key3", "value3");
		dataMap.put("key4", "value4");
		model.addAttribute("dataMap", dataMap);
		HttpSession session = request.getSession();
		session.setAttribute("sessionName", "ok");
		return "/platform/index";
	}
	
	@ResponseBody
	@RequestMapping("/two")
	public ResultData two(Model model, HttpServletRequest request) {
		ResultData success = ResultData.getSuccess();
		Page page = Page.getPage(request);
		Student student = new Student("张三", 23, "playgame");
		success.setData(student);
		success.setPage(page);
		System.out.println("123");
		return success;
	}
	
}
