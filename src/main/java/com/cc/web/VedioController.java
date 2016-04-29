package com.cc.web;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class VedioController {

	
	
	/**
	 * liveUrl 直播地址
	 * l_name  直播地址后续参名1
	 * l_val   直播地址后续参数值1
	 * 
	 * backUrl 跳转的链接
	 * 
	 * timestamp 时间戳
	 * 
	 * 示例link:
	 * http://127.0.0.1:8080/vedio?liveUrl=http://43.224.208.195/live/coship,TWSX1422589417980523.m3u8&l_name=fmt&l_val=x264_0k_mpegts
	 * 
	 * 
	 * */

	@RequestMapping("/vedio")
	public String welcome(Map<String, Object> model, HttpServletRequest request) {
		String liveUrl = request.getParameter("liveUrl") ;
		String l_name = request.getParameter("l_name") ;
		String l_val = request.getParameter("l_val") ;
		String liveUrl_all = liveUrl+"?"+l_name+"="+l_val;
		System.out.println(liveUrl_all); //	
 
		return "welcome";
	}
	
	
	
	/**	  
	 * 视频播放页面
	 * 
	 * 示例link:
	 * http://127.0.0.1:8080/see 
	 * 
	 * */

	@RequestMapping("/see")
	public String see(Map<String, Object> model, HttpServletRequest request) { 
		return "seevedio";
	}

}
