package com.cc;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class testTimestampe {

	public static void main_mm(String[] args) {	
		String s = "20160520150953";
		long timestampslong =  DataStringToTimestampLong(s) ;
		System.out.println(s+"-----》"+timestampslong+"521 (精确到秒)");
		//1461918177000   1461918177
		//1461918176000   1461918176
		
		//System.out.println(TimestampStringToTimestampLong("1461918177000")); 		
		//int iii =1461918176/10;
		//iii= iii*10;
		//System.out.println(iii);
	}
	
	public static long DataStringToTimestampLong(String  dataString) {
		long returnsrc_timestamp = 0L ;
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(new SimpleDateFormat("yyyyMMddHHmmss").parse(dataString));
			returnsrc_timestamp = Long.valueOf(c.getTimeInMillis());
			returnsrc_timestamp = returnsrc_timestamp /1000 ;
		} catch (ParseException e) {			
			e.printStackTrace();
		}		 
		return returnsrc_timestamp ;
	}
	
	
	public static long TimestampStringToTimestampLong(String  dataString) {
		 long time_second = Long.parseLong(dataString);
		 time_second = time_second/1000 ;
		 return time_second ;  
	}

}
