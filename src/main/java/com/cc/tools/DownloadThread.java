package com.cc.tools;

import java.text.ParseException;
import java.util.List;
import org.springframework.stereotype.Component;

import com.cc.dao.TslocalstatuspojoDao;
import com.cc.dao.TspojoDao;
import com.cc.entity.Tspojo;


//@Component
public class DownloadThread  extends Thread {
	
	private String m3u8url;  //下载全链接
	
	private String srcurl;  
	
	private int tsname_length; 
	
	private int timelength; 
	
	private String uuids;   
	
	//private List <Tspojo> tslist;   
	
	private Long nowtime; 
	
	private TspojoDao tspojoDao ;
	
	private TslocalstatuspojoDao tslocalstatuspojoDao ;
	  
	public DownloadThread(String m3u8url, String srcurl, int tsname_length, int timelength ,String uuids,Long nowtime ,TspojoDao tspojoDao,TslocalstatuspojoDao tslocalstatuspojoDao   ) {
		super();
		this.m3u8url = m3u8url;
		this.srcurl = srcurl;
		this.tsname_length = tsname_length;
		this.timelength = timelength;		
		this.uuids = uuids;
		this.nowtime = nowtime;		
		this.tspojoDao = tspojoDao ;
		this.tslocalstatuspojoDao = tslocalstatuspojoDao;
	}



	public void run() {
		
		M3u8Download m = new M3u8Download(); //new 出来的 ，里面需要的参数 需要被传进 ，注入不进去的。		 
		try {
			m.m3u8download(m3u8url,srcurl,tsname_length,timelength,uuids,nowtime, tspojoDao,tslocalstatuspojoDao);
		} catch (ParseException e) {			
			e.printStackTrace();
		}
	 }






	public String getM3u8url() {
		return m3u8url;
	}



	public void setM3u8url(String m3u8url) {
		this.m3u8url = m3u8url;
	}



	public String getSrcurl() {
		return srcurl;
	}



	public void setSrcurl(String srcurl) {
		this.srcurl = srcurl;
	}



	public int getTsname_length() {
		return tsname_length;
	}



	public void setTsname_length(int tsname_length) {
		this.tsname_length = tsname_length;
	}



	public int getTimelength() {
		return timelength;
	}



	public void setTimelength(int timelength) {
		this.timelength = timelength;
	}



	public String getUuids() {
		return uuids;
	}



	public void setUuids(String uuids) {
		this.uuids = uuids;
	}
	
	
	
}
