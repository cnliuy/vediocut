package com.cc;

public class ChangeEnvReadMe {
/**
 * com.cc.ChangeEnvReadMe
 * 
 * 1.
 * 	 视频片段的文件存放目录放在  com.cc.tools.M3u8Download 类中。
 * 	 由 M3u8Download.GoGetFileSavePath()直接获取  destfilePath 视频文件的存放目录。
 * 
 * 2.
 * 	 修改 application.properties 中关于 数据库的连接内容
 * 
 *  com.cc.Someconstant  download_threads 下载线程数  
 * 
 * 3. 
 * 	主类 com.cc.web Live2Controller入口
 * 
 * 4.
 * 	访问链接
http://127.0.0.1:8080/vepl/livex/liveclip?timelength=60&liveUrl=http%3A%2F%2F43.224.208.195%2Flive%2Fcoship%2CTWSX1422589417980523.m3u8%3Ffmt%3Dx264_0k_mpegts

http://10.0.0.35:8080/vepl/livex/liveclip?timelength=60&liveUrl=http%3A%2F%2F43.224.208.195%2Flive%2Fcoship%2CTWSX1422589417980523.m3u8%3Ffmt%3Dx264_0k_mpegts
http://127.0.0.1:8080/vepl/wel

http://127.0.0.1/livex/liveclip?timelength=60&liveUrl=http%3A%2F%2F43.224.208.195%2Flive%2Fcoship%2CTWSX1422589417980523.m3u8%3Ffmt%3Dx264_0k_mpegts
 
 服务器地址：
  http://211.148.171.93/vepl/wel 测试页面   不连数据库的
  http://211.148.171.93/vepl/livex/jtplayvedio?uid=b54b9662-5671-4251-90de-f8353457f124
  	发起页面：
  http://211.148.171.93/vepl/livex/liveclip?timelength=60&liveUrl=http%3A%2F%2F43.224.208.195%2Flive%2Fcoship%2CTWSX1422589417980523.m3u8%3Ffmt%3Dx264_0k_mpegts
  http://211.148.171.93/vepl/livex/liveclip?timelength=50&liveUrl=http%3A%2F%2F43.224.208.195%2Flive%2Fcoship%2CTWSX1422589417980523.m3u8%3Ffmt%3Dx264_0k_mpegts
	
  新发起页面
  http://211.148.171.93/vepl/livex/liveclip4
  
  http://127.0.0.1/livex/liveclip4
	

	视频源地址：
	http://43.224.208.195/live/coship,TWSX1422589417980523.m3u8?fmt=x264_0k_mpegts
	
	
	
 * 5.
 *   修改其他配置
 *   
 *   
 * 1).    inputtitle4.jsp 文件
 * 		位置:  \WEB-INF\jsp\inputtitle4.jsp 
 * 
 * 文件内有控制视频截取长短的设置，搭配链接见后：(  http://127.0.0.1/livex/liveclip4 
 * 				or 
 * 				http://211.148.171.93/vepl/livex/liveclip4 )
 *  
 * 	33行   “timelength=70” 这个参数 *  更换频道，修改  liveUrl 后面的参数 直播串
		$.get("<%=path %>/rest/tsdownloadstat?timelength=70&liveUrl=http%3A%2F%2F43.224.208.195%2Flive%2Fcoship%2CTWSX1422589417980523.m3u8%3Ffmt%3Dx264_0k_mpegts",function(data,status){
	
	47到62行
		var ref2 = setInterval(function(){
		   。。。。
		},6000);
	    6000 = 6秒 ，为轮询链接的时间。
 *  
 *  
 *  2).    com.cc.Someconstant
 *  	系统内部的常量设置
 *  
 * */
}
