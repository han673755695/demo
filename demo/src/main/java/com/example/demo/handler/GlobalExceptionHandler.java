package com.example.demo.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(Exception.class) // 所有的异常都是Exception子类
    public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception e) { // 出现异常之后会跳转到此方法
        ModelAndView mav = new ModelAndView("/errorPage"); // 设置跳转路径
        mav.addObject("exception", e.getMessage()); // 将异常对象传递过去
        mav.addObject("url", request.getRequestURL()); // 获得请求的路径
        return mav;
    }
}
