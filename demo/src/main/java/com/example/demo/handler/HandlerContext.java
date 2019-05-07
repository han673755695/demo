package com.example.demo.handler;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "person")
public class HandlerContext {

	private Map<String, String> maps;

	private String name;

	public Map<String, String> getMaps() {
		return maps;
	}

	public void setMaps(Map<String, String> maps) {
		this.maps = maps;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PlayGame getInstence(String type) {
		String string = maps.get(type);
		try {
			Class<?> forName = Class.forName(string);
			return (PlayGame) forName.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
