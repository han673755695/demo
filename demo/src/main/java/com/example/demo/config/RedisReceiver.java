package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import com.example.demo.handler.RedisMessageContext;
import com.example.demo.handler.RedisMessageHandle;

@Component
public class RedisReceiver implements MessageListener{

	private static final Logger logger = LoggerFactory.getLogger(RedisReceiver.class);
	
	@Autowired
	RedisMessageContext redisMessageContext;
	
	@Override
	public void onMessage(Message message, byte[] pattern) {
		logger.info("接受消息");
		String string = new String(pattern);
		
		RedisMessageHandle redisMessageHandle = new RedisMessageHandle(redisMessageContext.getInstence(string));
		redisMessageHandle.onMessage(message);
	}
}
