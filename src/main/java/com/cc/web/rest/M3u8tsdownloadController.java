package com.cc.web.rest;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cc.Someconstant;
import com.cc.dao.TslocalstatuspojoDao;
import com.cc.dao.TspojoDao;
import com.cc.entity.Tslocalstatuspojo;
import com.cc.tools.M3u8Download;

@RestController
@RequestMapping("/rest")
public class M3u8tsdownloadController {
	@Autowired
	private TslocalstatuspojoDao tslocalstatuspojoDao;
	@Autowired
	private TspojoDao tspojoDao;
	
	private int  tsname_length = Someconstant.tsname_length;
	
	private String srcurll = Someconstant.srcurl;
	
	private Integer errflag = 0;
	
	private Integer okflag = 1;
	
	
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
	
	
	
	
	
	/**
	 * 下载ts内容 并显示下载状态
	 * http://127.0.0.1:8080/rest/tsdownloadstat?timelength=
	 * 
	 * 
	 * timelength=60
	 * &
	 * liveUrl=http%3A%2F%2F43.224.208.195%2Flive%2Fcoship%2CTWSX1422589417980523.m3u8%3Ffmt%3Dx264_0k_mpegts
	 * 
 http://127.0.0.1:8080/rest/tsdownloadstat?timelength=30&liveUrl=http%3A%2F%2F43.224.208.195%2Flive%2Fcoship%2CTWSX1422589417980523.m3u8%3Ffmt%3Dx264_0k_mpegts
	 * 
	 * 
	 * */
	@RequestMapping("/tsdownloadstat")
	public String m3u8tsdownloadstat(HttpServletRequest request) {
		
		//获取时间间隔
		String timelength =  request.getParameter("timelength") ;
		Integer  tiemlengthi = Integer.parseInt(timelength);
		
		String liveUrL = request.getParameter("liveUrl") ;//URI可以自动转为 URL型的
		System.out.println("liveUrL:"+liveUrL);
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
				

		Integer  returnflag = errflag ;  // errflag okflag
		if("".equals(liveUrL) ){
			//地址转化出现问题  异常
			return returnflag.toString();  
		}else{
			M3u8Download m = new M3u8Download(); //new 出来的 ，里面需要的参数 需要被传进 ，注入不进去的。	
			String uuids = UUID.randomUUID().toString();					
			try {
				returnflag = m.m3u8download3(liveUrL,srcurll,tsname_length,tiemlengthi,uuids, tspojoDao,tslocalstatuspojoDao);
			} catch (ParseException e) {				
				e.printStackTrace();
			}
			return returnflag.toString();
								
		}

	}
}
