package com.example.demo.eunm;

public enum GameEnum {

	DanJi("DanJi"),
	WangLuo("WangLuo");
	private String value;
	
	GameEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
}
