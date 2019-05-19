package com.example.demo.handler;

import org.springframework.data.redis.connection.Message;

public interface RedisMessage {

	public void onMessage(Message message);
}
