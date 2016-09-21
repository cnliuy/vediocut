package com.cc.web.socket.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.WebSocketSession;

import com.cc.Someconstant;
import com.cc.tools.URItool;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;



@Controller
public class MsgController {
	
	/**
	 * 一对一时 用到  
	 * 
	 * Start
	 * */
    public SimpMessagingTemplate simpMessagingTemplate; 
    
    @Autowired  
    public MsgController(SimpMessagingTemplate simpMessagingTemplate) {  
        this.simpMessagingTemplate = simpMessagingTemplate;  
    }
    
    @MessageMapping("/signstatuser")  
    //@SendToUser("/message")  ,Principal principal
    public void  userMessage(String message) throws Exception {  
    	System.out.println("-------- in userMessage-----------------");
    	
       	ObjectMapper mapper = new ObjectMapper();  
    	Map map;	String name1="";	String timestamp="";
    	//{'name': name,'timestamp':timestamp}
        map = mapper.readValue(message, Map.class);
        name1 =  (String)map.get("name") ;
        timestamp =  ((Long)map.get("timestamp")).toString() ;
    	System.out.println("----"+name1+"---------"+timestamp);    	
    	//String dd= principal.getName();
    	//System.out.println(dd);   	
    	simpMessagingTemplate.convertAndSend("/topic/servicemsg" + timestamp, message);
    } 
    /**
     * 一对一
     * End 
     * */
    
    
	/**
	 * 测试1 
	 * 
	 * */
    @MessageMapping("/receivemsg")
    @SendTo("/topic/servicemsg")
    public String receiveMsg(String message) throws Exception {
        //Thread.sleep(3000); //simulated delay
        System.out.println("in  MsgController() --- Msg method, message:"+message);      
        return message;
    }
	/**
	 * 测试1 
	 * 	测试用
	 * 	供socket.html调用
	 * 
	 * */
    @MessageMapping("/signstat0")
    //--@SendTo("/topic/servicemsg")
    @SendToUser("/topic/servicemsg")    
    //@SendTo("/topic/sign")    , WebSocketSession session ,,HttpServletRequest request
    public String signStat0( String message ) throws Exception {
    	System.out.println("in  MsgController() --- signStat: message:"+message); 
    	
    	
    	message = "{'stat':'ok'}";
        return message;
    }
    
	/**
	 * 测试1 
	 * 
	 * 	用于视频截取时的前后台间的 通讯   供socket2.html调用
	 * 
	 * */
    @MessageMapping("/signstat")
    @SendTo("/topic/servicemsg")
    //@SendTo("/topic/sign")    , WebSocketSession session ,,HttpServletRequest request
    public String signStat( String message ) throws Exception {
    	System.out.println("in  MsgController() --- signStat: message:"+message);
    	//Thread.sleep(10000); 
    	ObjectMapper mapper = new ObjectMapper();  
		Map map;		
		String liveUrL = ""; 
		try {
			//{'timelength':'70', 'liveUrl':'http%3A%2F%2F43.224.208.195%2Flive%2Fcoship%2CTWSX1422589417980523.m3u8%3Ffmt%3Dx264_0k_mpegts'}));
	        map = mapper.readValue(message, Map.class);
			System.out.println(map.get("timelength") + ":" + map.get("liveUrl")); 			
			String timelength =  (String) map.get("timelength") ;			
			liveUrL = (String) map.get("liveUrl");
			liveUrL = URItool.URI2URL(liveUrL);//URI可以自动转为 URL型的
			System.out.println("liveUrL:"+liveUrL);
			System.out.println("timelength:"+timelength);
		} catch (JsonParseException e1) {
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}   	
    	
    	message = "{'stat':'ok'}";
        return message;
    }
}
