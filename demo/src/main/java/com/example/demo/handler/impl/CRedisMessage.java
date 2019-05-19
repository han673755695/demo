package com.example.demo.handler.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;

import com.example.demo.handler.RedisMessage;

public class CRedisMessage implements RedisMessage {

	private static final Logger logger = LoggerFactory.getLogger(CRedisMessage.class);
	
	
	@Override
	public void onMessage(Message message) {
		logger.info("CRedisMessage类型处理消息");
		System.out.println(message);
	}

}
