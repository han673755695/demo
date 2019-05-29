package com.example.demo.eunm;


/**
 * 常用的枚举
 * @author my
 *
 */
public class CommonEunm {

	/**
	 * 菜单是否是按钮
	 * @author my
	 *
	 */
	public static enum menuType{
		菜单("1"),按钮("2");
		private String value;
		private menuType(String value){
			this.value = value;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
	}
}
