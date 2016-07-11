package com.cc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * http://127.0.0.1:8080/vepl/livex/liveclip?timelength=60&liveUrl=http%3A%2F%2F43.224.208.195%2Flive%2Fcoship%2CTWSX1422589417980523.m3u8%3Ffmt%3Dx264_0k_mpegts
 * http://127.0.0.1:8080/vepl/wel
 * 
 * 
 * http://127.0.0.1/livex/liveclip?timelength=60&liveUrl=http%3A%2F%2F43.224.208.195%2Flive%2Fcoship%2CTWSX1422589417980523.m3u8%3Ffmt%3Dx264_0k_mpegts
 * */
@SpringBootApplication
//@EnableScheduling
//public class VediocutApplication  extends WebMvcConfigurerAdapter {
public class VediocutApplication  extends SpringBootServletInitializer{
	public static void main(String[] args) {
		SpringApplication.run(VediocutApplication.class, args);
		//@EnableScheduling打开注释， 启动后 ，ScheduledTasks类 自动开启定时任务： 下载m3u8文件，同时下载相应的ts，并保存到数据库 。重复的文件不做考虑。
	}
	
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//	    registry.addResourceHandler("/mystatic/**")
//	            .addResourceLocations("classpath:/mystatic/");
//	}
	
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(VediocutApplication.class);
    }
}
