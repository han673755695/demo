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
	MENUKEY("menuList"),
	/**
	 * 全部菜单包含按钮
	 */
	MENUKEY_ALL("menuListAll"),
	
	/**
	 * 订阅发布主题名称
	 */
	MESSAGEPUSH_A("messagepush_a"),
	/**
	 * 订阅发布主题名称
	 */
	MESSAGEPUSH_B("messagepush_b"),
	/**
	 * 订阅发布主题名称
	 */
	MESSAGEPUSH_C("messagepush_c");
	
	private String value;
	
	RedisKeyEnum(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
