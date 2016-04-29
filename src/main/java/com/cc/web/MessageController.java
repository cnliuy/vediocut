package com.cc.web;

import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class MessageController {

	
	
	@Value("${application.message}")
	private String message ;

	@RequestMapping("/wel")
	public String welcome(Map<String, Object> model) {
		model.put("time", new Date());
		model.put("message", this.message);
		System.out.println(this.message);
		return "welcome";
	}

}
