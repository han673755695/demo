package com.example.demo.common;

import java.util.List;
import java.util.Map;

/**
 * 兼容layui的table插件数据要求格式
 * @author my
 *
 */
public class TableResultData {

	private int code;
	
	private String mag;
	
	private int count;
	
	private List<Map<String,Object>> data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMag() {
		return mag;
	}

	public void setMag(String mag) {
		this.mag = mag;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<Map<String, Object>> getData() {
		return data;
	}

	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "TableResultData [code=" + code + ", mag=" + mag + ", count=" + count + ", data=" + data + "]";
	}
	
}
