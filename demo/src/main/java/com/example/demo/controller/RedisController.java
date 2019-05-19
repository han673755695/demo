package com.example.demo.controller;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.common.ResultData;
import com.example.demo.eunm.RedisKeyEnum;
import com.example.demo.utils.RedisUtils;
import com.example.demo.utils.UUIDUtils;


@Controller
@RequestMapping("/redisController")
public class RedisController {

	private static final Logger logger =  LoggerFactory.getLogger(RedisController.class);
	
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	@Autowired
	RedisTemplate redisTemplate;

	@RequestMapping("/addCoupon")
	@ResponseBody
	public ResultData addCoupon(HttpServletRequest request) {
		ResultData success = ResultData.getSuccess();
		try {
			for (int i = 0; i < 50; i++) {
				RedisUtils.setSet("coupon", UUIDUtils.getUUID());
			}
		} catch (Exception e) {
			success.setStatus(ResultData.ERROR);
			e.printStackTrace();
		}
		return success;
	}

	@RequestMapping("/getCoupon")
	@ResponseBody
	public ResultData getCoupon(HttpServletRequest request) {
		ResultData success = ResultData.getSuccess();
		try {
			String popSet = RedisUtils.getPopSet("coupon");
			success.setData(popSet);
			RedisUtils.setString("user_coupon", popSet, 10l);

		} catch (Exception e) {
			success.setStatus(ResultData.ERROR);
			e.printStackTrace();
		}
		return success;
	}

	
	/**
	 * 使用redis模拟排行榜
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addRanking")
	public ResultData addRanking(HttpServletRequest request) {
		ResultData success = ResultData.getSuccess();
		try {
			logger.info("模拟创建排行榜");
			//创建排行榜	点击每条热点的时候添加进来,每次增加1
			ZSetOperations<String,String> opsForZSet = redisTemplate.opsForZSet();
			opsForZSet.incrementScore("ranking", "王五", 50d);
			opsForZSet.incrementScore("ranking", "王八", 80d);
			opsForZSet.incrementScore("ranking", "李四", 40d);
			opsForZSet.incrementScore("ranking", "钱七", 70d);
			opsForZSet.incrementScore("ranking", "张三", 30d);
			opsForZSet.incrementScore("ranking", "赵六", 60d);

			
			opsForZSet.incrementScore("ranking", "张三", 100d);
			logger.info("模拟修改排行榜");
			
			//倒序取出前几条
			Set<String> range = opsForZSet.reverseRange("ranking", 0, -1);
			logger.info("模拟获取排行榜");
			
			for (String string : range) {
				System.out.println(string);
			}
			
			//当该热点信息被删除的时候,记得移除排行榜里面的元素
			opsForZSet.remove("ranking", "张三");
			logger.info("模拟删除排行榜中的数据");
			
			success.setData(range);
		} catch (Exception e) {
			success.setStatus(ResultData.ERROR);
			e.printStackTrace();
		}
		return success;
	}
	
	
	/**
	 * 模拟分布式锁的实现
	 * @return
	 */
	@RequestMapping("/getLock")
	@ResponseBody
	public ResultData getLock() {
		ResultData success = ResultData.getSuccess();
		String valueOf = String.valueOf(new Date().getTime() + 10000);
		try {
			Boolean setIfAbsent = stringRedisTemplate.opsForValue().setIfAbsent("name", valueOf, 10l, TimeUnit.SECONDS);
			if (setIfAbsent) {
				logger.info("获取锁");
				
				logger.info("执行完毕业务");
				
			}else {
				logger.info("获取锁失败");
			}
			
			success.setData(setIfAbsent);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			logger.info("释放锁");
			stringRedisTemplate.delete("name");
		}
		
		return success;
	}
	
	
	
	@RequestMapping("/messagepush")
	@ResponseBody
	public ResultData messagepush() {
		ResultData success = ResultData.getSuccess();
		stringRedisTemplate.convertAndSend(RedisKeyEnum.MESSAGEPUSH_A.getValue(), "aetregdg!");
		
		return success;
	}
	

}
