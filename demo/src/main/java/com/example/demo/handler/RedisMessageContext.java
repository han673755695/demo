package com.example.demo.handler;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "redismessage")
public class RedisMessageContext {

	private Map<String, String> maps;

	public Map<String, String> getMaps() {
		return maps;
	}

	public void setMaps(Map<String, String> maps) {
		this.maps = maps;
	}
	
	public RedisMessage getInstence(String type) {
		String string = maps.get(type);
		try {
			Class<?> forName = Class.forName(string);
			return (RedisMessage) forName.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
