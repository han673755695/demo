package com.example.demo.handler;

import org.springframework.data.redis.connection.Message;

public class RedisMessageHandle {

	private RedisMessage redisMessage;
	
	public RedisMessageHandle(RedisMessage redisMessage) {
		this.redisMessage = redisMessage;
	}
	
	public void onMessage(Message message) {
		redisMessage.onMessage(message);
	}
}
