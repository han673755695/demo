package com.example.demo.controller;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.common.ResultData;
import com.example.demo.utils.RedisUtils;
import com.example.demo.utils.RequestParamUtils;

@Controller
@RequestMapping("/redisController")
public class RedisController {

	@Autowired
    StringRedisTemplate stringRedisTemplate;
	
	@RequestMapping("/getData")
	@ResponseBody
	public ResultData getData(HttpServletRequest request) {
		ResultData success = ResultData.getSuccess();
		try {
			RedisUtils.setString("test", "ok");
			String string = RedisUtils.getString("test");
			System.out.println(string);
		} catch (Exception e) {
			success.setStatus(ResultData.ERROR);
			e.printStackTrace();
		}
		return success;
	}
	
}
