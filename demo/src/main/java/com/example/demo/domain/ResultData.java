package com.example.demo.domain;

import java.util.Map;

public class ResultData {

	private static String SUCCESS = "success";
	private static String ERROR = "error";

	// 存放数据map
	private Map<String, Object> mapData;
	// 存放数据
	private Object data;
	// 返回状态
	private String status;
	// 返回分页信息
	private Page page;
	// 请求参数
	private Object queryParams;
	// 返回信息
	private String message = "返回成功";

	public ResultData() {

	}

	public Object getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(Object queryParams) {
		this.queryParams = queryParams;
	}

	public ResultData(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, Object> getMapData() {
		return mapData;
	}

	public void setMapData(Map<String, Object> mapData) {
		this.mapData = mapData;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	/**
	 * 获取返回成功实体类
	 * 
	 * @return
	 */
	public static ResultData getSuccess() {
		return new ResultData(SUCCESS);
	}

	/**
	 * 获取返回失败实体类
	 * 
	 * @return
	 */
	public static ResultData getError() {
		return new ResultData(ERROR);
	}

	@Override
	public String toString() {
		return "ResultData [mapData=" + mapData + ", data=" + data + ", status=" + status + ", page=" + page
				+ ", message=" + message + "]";
	}

}
