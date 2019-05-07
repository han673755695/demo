package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
		System.out.println("进入freemarker首页");
		model.addAttribute("name", "张三");
		List list = new ArrayList();
		list.add(0);
		list.add(1);
		list.add(2);
		model.addAttribute("list", list);
		return "index";
	}
	
	@ResponseBody
	@RequestMapping("/two")
	public ResultData two(Model model, HttpServletRequest request) {
		ResultData success = ResultData.getSuccess();
		Page page = Page.getPage(request);
		Student student = new Student("张三", 23, "playgame");
		success.setData(student);
		success.setPage(page);
		return success;
	}
	
}
