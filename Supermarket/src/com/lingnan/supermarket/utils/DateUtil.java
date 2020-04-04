package com.lingnan.supermarket.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	
	/**
	 * 格式化日期
	 * @param date     
	 * @param pattern  
	 * @return
	 */
	public static String dateToString(Date date,String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
		simpleDateFormat.applyPattern(pattern==null?"yyyy-MM-dd HH:mm:ss":pattern);
		return simpleDateFormat.format(date);
	}
	
	
}
