package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.eunm.GameEnum;
import com.example.demo.handler.Handle;
import com.example.demo.handler.HandlerContext;

@Controller
@RequestMapping("/test")
public class HandlerController {

	@Autowired
	private HandlerContext handlerContext;
	
	@ResponseBody
	@RequestMapping("/index")
	public String index() {
		Handle handle = new Handle(handlerContext.getInstence(GameEnum.DanJi.getValue()));
		String doSomething = handle.doSomething();
		return doSomething;
	}
	
}
