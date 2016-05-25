package com.cc.tool.sechedule;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cc.Someconstant;
import com.cc.tools.Remotefiletools;
import com.cc.tools2.M3u8DownloadC;

@Component
public class ScheduledTasks {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
 
	@Autowired
	private Remotefiletools remotefiletools;
	@Autowired
	private M3u8DownloadC m3u8DownloadC;

    
	/**
	 * 每隔 16秒 ，取一次m3u8 视频，并存入数据库。
	 * 
	 * */
    @Scheduled(fixedRate = 16000)
    public void downm3u8save2db() {
    	
     	String url ="http://43.224.208.195/live/coship,TWSX1422589417980523.m3u8?fmt=x264_0k_mpegts";
    	String srcurl ="http://43.224.208.195/";
    	
    	try {  
        	//下载 m3u8文件，并下载相应视频，存入数据库。重复的ts丢掉，不下载，不存数据库。 			
			m3u8DownloadC.m3u8download(url,srcurl,Someconstant.tsname_length);	   	
		} catch (java.text.ParseException e) {		
			e.printStackTrace();
		}       
    	
    }
    
    
    
	/**
	 * 每隔 20秒 ，取一次m3u8 视频，并下载到指定文件夹 同属存入数据库。
	 * 
	 * */
    //@Scheduled(fixedRate = 20000)
    public void downm3u8anddownloadts() {
    	//Tspojo t = new Tspojo();
    	//t.setName("1231232");
    	
    	long s = System.nanoTime();//---计时器开始
    	String url ="http://43.224.208.195/live/coship,TWSX1422589417980523.m3u8?fmt=x264_0k_mpegts";
    	String srcurl ="http://43.224.208.195/";
    	//下载后切片放置的目录
    	String destFilePath="F:"+File.separator+"Java2016"+File.separator+"src"+File.separator+"vediocut"+
    							File.separator+"vediocut"+File.separator+"src"+File.separator+"main"+
    							File.separator+"resources"+File.separator+"static"+File.separator+"live"
    							+File.separator+"live2"+File.separator+"TJ2"+File.separator+"800";   
    	try {
    		if (!(new File(destFilePath).isDirectory())) {
    			new File(destFilePath).mkdir();
    		}
    	} catch (SecurityException e) {
    		   e.printStackTrace();
    	}
    	System.out.println("目录path："+destFilePath);

        try {  
        	//下载 m3u8文件，并下载相应视频，存入数据库。重复的ts丢掉，不下载，不存数据库。
        	remotefiletools.getM3u8_DownFiles(url,srcurl,destFilePath);        	
        } catch (ParseException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        
        long d = System.nanoTime() - s;//---计时器结束
		System.out.println("花费时间 ="+(d/1000000)+"毫秒");   	
        System.out.println("你好 The time is now " + dateFormat.format(new Date()));
    }
    

    

}