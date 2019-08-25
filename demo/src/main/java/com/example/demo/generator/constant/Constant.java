package com.example.demo.generator.constant;

public class Constant {
	
	/**
	 * 	模板路径位置
	 * @author my
	 *
	 */
	public static enum GeneratorEnum{
		新增("/code-generator/ui/bootstrap-ui.html"), 查看("/code-generator/ui/bootstrap-ui.html"), 
		mapperXml("/code-generator/mybatis/MenuMapper.html");
		private String value;
		
		GeneratorEnum(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
	}
	
	/**
	 *  生成的文件位置
	 * @author my
	 *
	 */
	public static enum createFilePathEnum{
		mapper文件("D:/templates/mapperXml/");
		private String value;
		
		createFilePathEnum(String value) {
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
