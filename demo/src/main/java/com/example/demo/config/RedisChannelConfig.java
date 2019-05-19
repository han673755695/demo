package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisServer;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

import com.example.demo.eunm.RedisKeyEnum;

@Component
public class RedisChannelConfig {
	/**
	 * redis消息监听器容器 可以添加多个监听不同话题的redis监听器，只需要把消息监听器和相应的消息订阅处理器绑定，该消息监听器
	 * 通过反射技术调用消息订阅处理器的相关方法进行一些业务处理
	 * 
	 * @param connectionFactory
	 * @param listenerAdapter
	 * @return
	 */
	@Bean
	RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);

		// 可以添加多个 messageListener
		container.addMessageListener(listenerAdapter, new PatternTopic(RedisKeyEnum.MESSAGEPUSH_A.getValue()));
		container.addMessageListener(listenerAdapter, new PatternTopic(RedisKeyEnum.MESSAGEPUSH_B.getValue()));
		container.addMessageListener(listenerAdapter, new PatternTopic(RedisKeyEnum.MESSAGEPUSH_C.getValue()));

		return container;
	}

	/**
	 * 消息监听器适配器，绑定消息处理器，利用策略模式来处理消息
	 * 
	 * @param redisReceiver
	 * @return
	 */
	@Bean
	MessageListenerAdapter listenerAdapter(RedisReceiver redisReceiver) {
		return new MessageListenerAdapter(redisReceiver);
	}
}
