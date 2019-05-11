package com.example.demo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public final static String PATTERN_SHORT = "yyyy-MM-dd";
	public final static String PATTERN_DEFAULT = "yyyy-MM-dd HH:mm:ss";
	
	public DateUtils() {
		
	}
	
	/**
	  * 把日期格式化成字符串
	 * @param pattern
	 * @param date
	 * @return
	 */
	public static String date2StringFormat(String pattern, Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}
	
	/**
	  * 把日期格式化成字符串
	 * @param pattern
	 * @param date
	 * @return
	 */
	public static String date2StringFormat(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN_DEFAULT);
		return simpleDateFormat.format(date);
	}
	
	/**
	 * 字符串类型转换成日期
	 * @param pattern
	 * @param dateStr
	 * @return
	 */
	public static Date string2Date(String pattern, String dateStr) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		try {
			return simpleDateFormat.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 字符串类型转换成日期
	 * @param dateStr
	 * @return
	 */
	public static Date string2Date(String dateStr) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN_DEFAULT);
		try {
			return simpleDateFormat.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
