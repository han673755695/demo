package com.example.demo.utils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
  * 封装请求参数成map
 * @author my
 *
 */
public class RequestParamUtils {

	private static Logger logger = LoggerFactory.getLogger(RequestParamUtils.class);
	
	private RequestParamUtils() {

	}

	/**
	 * 获取前台提交的参数
	 * 
	 * @return
	 */
	public static Map<String,Object> getParameterMap(HttpServletRequest request) {
		Map<String,Object> parametersMap = new HashMap();
		Enumeration paramters = request.getParameterNames();
		while (paramters.hasMoreElements()) {
			String name = (String) paramters.nextElement();
			String[] values = request.getParameterValues(name);
			String value = "";
			if (values != null) {
				for (int i = 0; i < values.length; i++) {
					value += values[i] + ',';
				}
				value = value.substring(0, value.length() - 1);
			}
			parametersMap.put(name, value.trim());
		}
		logger.info("========parametersMap: " + parametersMap);
		return parametersMap;
	}

}
