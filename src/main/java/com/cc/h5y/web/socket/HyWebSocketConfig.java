package com.cc.h5y.web.socket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;


/**
 * spring boot Websocket（使用笔记） 
 * 
 * http://www.cnblogs.com/bianzy/p/5822426.html
 * 
 * 参考 
 * */
@Configuration
public class HyWebSocketConfig {
	@Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
