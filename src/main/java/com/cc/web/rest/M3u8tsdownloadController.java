package com.cc.web.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cc.dao.TslocalstatuspojoDao;
import com.cc.entity.Tslocalstatuspojo;

@RestController
@RequestMapping("/rest")
public class M3u8tsdownloadController {
	@Autowired
	private TslocalstatuspojoDao tslocalstatuspojoDao;
	
	/**
	 * http://127.0.0.1:8080/rest/downloadbeok?vediotimestamp=1464231513
	 * 
	 * @param  nowtime  ---  vediotimestamp
	 * 
	 * */
	@RequestMapping("/downloadbeok")
	public String m3u8downloadbeok(HttpServletRequest request) {
	  String nowtime = request.getParameter("vediotimestamp");
	  Long nowtimel = Long.parseLong(nowtime);
	  List <Tslocalstatuspojo> tslocalstatuspojo = tslocalstatuspojoDao.findByTstimesecond(nowtimel);
	  if(tslocalstatuspojo == null || tslocalstatuspojo.size() == 0){
		  return "0";
	  }else{
		  if(tslocalstatuspojo.get(0) != null){
		
			 if ( tslocalstatuspojo.get(0).getTsstat() == 1 ){
				 return "1";
			 }else{
				 return "0";
			 }
		  }else{
			  return "0";
		  }
		 
	  }	
	  //return "Greetings from Spring Boot!";
	}
}
