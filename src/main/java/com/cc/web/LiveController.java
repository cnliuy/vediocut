package com.cc.web;

import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cc.dao.TspojoDao;



@Controller
@RequestMapping("/live")
public class LiveController {
	@Autowired
	private TspojoDao tspojoDao;
	
	
	/**
	 * 直播串
	 * 
	 * 生成 m3u8 串 
	 * 	http://127.0.0.1:8080/live/TJ2-800-node1.m3u8
	 * 
	 * */

	@RequestMapping("/TJ2-800-node1.m3u8")
	public String welcome(Map<String, Object> model, HttpServletRequest request) {

		System.out.println("------------===============0000000"); //	
 
		return "TJ2800node1";
	}
	
	


}
