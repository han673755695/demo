package com.example.demo.utils;

import java.util.UUID;

public class UUIDUtils {

	private UUIDUtils() {
		
	}
	
	/**
	 * 获取随机的32位字符串
	 * @return
	 */
	public static String getUUID() {
		
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
