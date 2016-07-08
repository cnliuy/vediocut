package com.cc.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class gettimestamp {

    public static void main_mm(String[] args) {
        String timestamp = String.valueOf(System.currentTimeMillis());
         //System.out.println(timestamp ); 
        String yifei_url = "http://eshare.h5.otvcloud.com/qscontents/jcs/yangma/sharing.html?liveUrl=" ;
        yifei_url = "http://eshare.h5.otvcloud.com/qscontents/jcs/tianguang/sharing.html?liveUrl=" ; //新地址
        String duoping_stream = "http://43.224.208.195/live/coship,TWSX1422595673115099.m3u8?fmt=x264_0k_mpegts" ;
        boolean flag = false ;
        try {
        	flag = true;
        	duoping_stream = URLEncoder.encode(duoping_stream, "utf-8");
		} catch (UnsupportedEncodingException e) {			
			e.printStackTrace();
		}
        String addprams = "&backUrl=mbh5.cmjs.ysten.com&timestamp=" +timestamp ;
        String resultstr ;
        if (flag){
        	resultstr = yifei_url+ duoping_stream+ addprams;
        }else{
        	resultstr = "false";
        }
        System. out.println("生成分享链接:\n"+ resultstr);
  }


}
