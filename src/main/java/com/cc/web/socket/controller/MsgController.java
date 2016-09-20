package com.cc.web.socket.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.WebSocketSession;



@Controller
public class MsgController {
	
    @MessageMapping("/receivemsg")
    @SendTo("/topic/servicemsg")
    public String receiveMsg(String message) throws Exception {
        //Thread.sleep(3000); //simulated delay
        System.out.println("in  MsgController() --- Msg method, message:"+message);      
        return message;
    }
    
    
    @MessageMapping("/signstat")
    @SendTo("/topic/servicemsg")
    //@SendTo("/topic/sign")    , WebSocketSession session ,
    public String signStat( String message) throws Exception {
    	System.out.println("in  MsgController() --- signStat: message:"+message);
    	//Thread.sleep(10000); 
    	message = "{'stat':'ok'}";
        return message;
    }
}
