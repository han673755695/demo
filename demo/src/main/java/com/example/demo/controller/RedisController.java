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
			Map<String,String> parameterMap = RequestParamUtils.getParameterMap(request);
			Set<String> keySet = parameterMap.keySet();
			for (String key : keySet) {
				stringRedisTemplate.opsForValue().set(key, parameterMap.get(key));
			}
			
		} catch (Exception e) {
			success.setStatus(ResultData.ERROR);
			e.printStackTrace();
		}
		return success;
	}
	
}
