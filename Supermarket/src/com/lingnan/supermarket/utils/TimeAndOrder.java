package com.lingnan.supermarket.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class TimeAndOrder {
	public static String[] TimeAndOrder(String username) {
		// TODO Auto-generated method stub
		
		String[] s = new String[2];
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式	
		Calendar cal = Calendar.getInstance();
		String date1 = sdf1.format(cal.getTime());
		String date2 = sdf2.format(cal.getTime());

		Random random=new Random();
		int result1=random.nextInt(10);
		int result2=random.nextInt(10);

		s[0]=username+result1+date2+result2;
		s[1]=date1;
		System.out.println(s[0]);
		System.out.println(s[1]);

		return s;

	}
	
	public static String yMdTime() {
		// TODO Auto-generated method stub
		
		String[] s = new String[2];
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String date = sdf1.format(cal.getTime());

		return date;

	}

}
