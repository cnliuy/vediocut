package com.cc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableScheduling
public class VediocutApplication  extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(VediocutApplication.class, args);
		//启动后 ，ScheduledTasks类 自动开启定时任务： 下载m3u8文件，同时下载相应的ts，并保存到数据库 。重复的文件不做考虑。
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/mystatic/**")
	            .addResourceLocations("classpath:/mystatic/");
	}
}
