package com.example.demo.config;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import com.example.demo.eunm.RedisKeyEnum;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * redis相关的配置类
 * @author my
 *
 */
@Component
public class RedisConfig implements RedisSerializer<Object> {

	/**
	 * redis自定义序列化
	 */
	@Override
	public byte[] serialize(Object o) throws SerializationException {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream objOut;
		try {
			objOut = new ObjectOutputStream(byteOut);
			objOut.writeObject(o);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return byteOut.toByteArray();
	}

	/**
	 * redis自定义反序列化
	 */
	@Override
	public Object deserialize(byte[] bytes) throws SerializationException {
		if (bytes == null)
			return null;
		ByteArrayInputStream byteIn = new ByteArrayInputStream(bytes);
		ObjectInputStream objIn;
		Object obj;
		try {
			objIn = new ObjectInputStream(byteIn);
			obj = objIn.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return obj;
	}

	
	/**
	 * 解决redis存储的key不可阅读问题
	 * @param redisConnectionFactory
	 * @return
	 */
	@Bean
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		// 1.创建 redisTemplate 模版
		RedisTemplate<Object, Object> template = new RedisTemplate<>();
		// 2.关联 redisConnectionFactory
		template.setConnectionFactory(redisConnectionFactory);
		// 3.创建 序列化类
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
				Object.class);
		ObjectMapper om = new ObjectMapper();
		// 4.设置可见度
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		// 5.启动默认的类型
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		// 6.序列化类，对象映射设置
		jackson2JsonRedisSerializer.setObjectMapper(om);
		// 7.设置 value 的转化格式和 key 的转化格式
		template.setValueSerializer(jackson2JsonRedisSerializer);
		template.setKeySerializer(new StringRedisSerializer());
		template.afterPropertiesSet();
		return template;
	}
	
	
	/**
	 * redis消息监听器容器 可以添加多个监听不同话题的redis监听器<br>
	 * 只需要把消息监听器和相应的消息订阅处理器绑定，该消息监听器<br>
	 * 通过反射技术调用消息订阅处理器的相关方法进行一些业务处理<br>
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
