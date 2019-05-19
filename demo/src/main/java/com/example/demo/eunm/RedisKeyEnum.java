package com.example.demo.eunm;


/**
 * redis中用到的key
 * @author my
 *
 */
public enum RedisKeyEnum {

	/**
	 * 菜单div键名key
	 */
	MENUKEY("menuDiv");
	
	private String value;
	
	RedisKeyEnum(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
