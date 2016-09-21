package com.cc.tools;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Date;

public class URItool {
	/**
	 * 入参
	 * http%3A%2F%2F43.224.208.195%2Flive%2Fcoship%2CTWSX1422595673115099.m3u8%3Ffmt%3Dx264_0k_mpegts
	 * @throws MalformedURLException 
	 *
	 * **/
	public static void main_mm(String[] args) throws MalformedURLException {
		String URIString = "http%3A%2F%2F43.224.208.195%2Flive%2Fcoship%2CTWSX1422595673115099.m3u8%3Ffmt%3Dx264_0k_mpegts";  
		System.out.println(URItool.URI2URL(URIString) );
		
		try {
			URI urii = new URI("http://127.0.0.1:8080/live/TJ2-800-vedioclip.m3u8?timelength=60&timestamp=1462331693523");
			System.out.println(urii.toURL());
		} catch (URISyntaxException e) {
			
			e.printStackTrace();
		}
		
		
		
		Date date = new Date();       
		Long nowtimestamp = date.getTime() ;
		
		System.out.println(nowtimestamp);
		
		//加密
		String s  ="http://43.224.208.195/live/coship,TWSX1422589417980523.m3u8?fmt=x264_0k_mpegts";
		 try {
			System.out.println(URLEncoder.encode(s,"UTF-8"));
			//http%3A%2F%2F43.224.208.195%2Flive%2Fcoship%2CTWSX1422589417980523.m3u8%3Ffmt%3Dx264_0k_mpegts
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static String URI2URL(String URIString) {		 
	    URI uri;
	    //String urlstring = "http://43.224.208.195/live/coship,TWSX1422589417980523.m3u8?fmt=x264_0k_mpegts";
	    String urlstring = "";
		try {
			uri = new URI(URIString);
			urlstring = uri.getPath() ;  //解码  
			//System.out.println("==============="+URIString);
			//System.out.println("==============="+urlstring);
		    //打印的内容：  http://43.224.208.195/live/coship,TWSX1422595673115099.m3u8?fmt=x264_0k_mpegts
		} catch (URISyntaxException e) {		
			e.printStackTrace();
		} 
		return urlstring;
	}

}
