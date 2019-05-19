package com.example.demo.config;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RedisSerializerConfig implements RedisSerializer<Object> {

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

}
