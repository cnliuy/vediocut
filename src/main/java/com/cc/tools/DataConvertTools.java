package com.cc.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
/**
 * 时间转化工具
 * 
 * */
public class DataConvertTools {
	/**
	 * 从 20160429162257  String转换为
	 * 1461918177000 long
	 * 
	 * */
	public static long DataStringToTimestampLong(String  dataString) {
		long returnsrc_timestamp = 0L ;
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(new SimpleDateFormat("yyyyMMddHHmmss").parse(dataString));
			returnsrc_timestamp = Long.valueOf(c.getTimeInMillis());
			returnsrc_timestamp = returnsrc_timestamp/1000; //精确到秒
		} catch (ParseException e) {			
			e.printStackTrace();
		}		 
		return returnsrc_timestamp ;
	}
	
	/**
	 * 从 1461918177000 String 转为 1461918177 long
	 * 
	 * 精确到秒
	 * */
	public static long TimestampStringToTimestampLong(String  dataString) {
		 long time_second = Long.parseLong(dataString);
		 time_second = time_second/1000 ;
		 return time_second ;  
	}
}
