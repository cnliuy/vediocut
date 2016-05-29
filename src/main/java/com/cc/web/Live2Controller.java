package com.cc.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cc.Someconstant;
import com.cc.dao.TslocalstatuspojoDao;
import com.cc.dao.TspojoDao;
import com.cc.entity.Tspojo;
import com.cc.tools.DataConvertTools;
import com.cc.tools.DownloadThread;
import com.cc.tools.M3u8Download;
import com.cc.tools.URItool;





/**
 * 视频截取 播放 类2  新
 * 
 * 
http://127.0.0.1:8080/livex/liveclip?timelength=60&liveUrl=http%3A%2F%2F43.224.208.195%2Flive%2Fcoship%2CTWSX1422589417980523.m3u8%3Ffmt%3Dx264_0k_mpegts

http://10.0.0.35:8080/livex/liveclip?timelength=60&liveUrl=http%3A%2F%2F43.224.208.195%2Flive%2Fcoship%2CTWSX1422589417980523.m3u8%3Ffmt%3Dx264_0k_mpegts

 * 
 * */
@Controller
@RequestMapping("/livex")
public class Live2Controller {
	private  String ipaddress = "10.0.0.35";
	private  String ipport = "8080";
	
	@Autowired
	private TspojoDao tspojoDao;
	
	@Autowired
	private TslocalstatuspojoDao tslocalstatuspojoDao;
	
	
	@Autowired
	private M3u8Download m3u8Download; //不能新建 ，new出来的，其中的注入会不好使
	
	//@Autowired  //多线程的话 此处不能注入 会报错 
	//private DownloadThread downloadThread;
	
	private Integer ts_per_time = 5; //ts 视频切片的间隔 5秒
	
	private String  lineSeparator = System.getProperty("line.separator", "/n"); 
	private int  lineSeparatorlen = lineSeparator.length(); 
	
	private int  tsname_length = Someconstant.tsname_length;
	private String srcurl="http://43.224.208.195/";
	
	/**
	 * 入口
	 * 
	 * 方法1 
	 *  异步下载ts切片
	 * 
	 * 生成 m3u8 串 
http://127.0.0.1:8080/livex/liveclip?liveUrl=http%3A%2F%2F43.224.208.195%2Flive%2Fcoship%2CTWSX1422595673115099.m3u8%3Ffmt%3Dx264_0k_mpegts&timelength=60
http://127.0.0.1:8080/livex/TJ2-800-vedioclip.m3u8?timelength=60&timestamp=1462331693523
http%3A%2F%2F43.224.208.195%2Flive%2Fcoship%2CTWSX1422589417980523.m3u8%3Ffmt%3Dx264_0k_mpegts  天津1 
http://43.224.208.195/live/coship,TWSX1422589417980523.m3u8?fmt=x264_0k_mpegts
---
http://127.0.0.1:8080/livex/liveclip?timelength=60&liveUrl=http%3A%2F%2F43.224.208.195%2Flive%2Fcoship%2CTWSX1422589417980523.m3u8%3Ffmt%3Dx264_0k_mpegts
	 * 
	 *  
	 *  需要截取的时长      timelength  X秒 
	 *  
	 *  @param 
	 *  	tiemlength 时长 精确到秒
	 *  	liveUrl 直播地址  URI形式 需要变为URL型 
	 * 		---http%3A%2F%2F43.224.208.195%2Flive%2Fcoship%2CTWSX1422595673115099.m3u8%3Ffmt%3Dx264_0k_mpegts
	 * 
	 * 
	 * */

	@RequestMapping("/liveclip")
	public Callable<String> 	productVedioClip(Map<String, Object> model, HttpServletRequest request) {	 
		 
		//获取时间间隔
		String timelength =  request.getParameter("timelength") ;
		Integer  tiemlengthi = Integer.parseInt(timelength);
		Long nowtime =  System.currentTimeMillis()/1000 ;//精确到秒
		model.put("vediotimestamp",nowtime.toString());
		String liveUrL = request.getParameter("liveUrl") ;//URI可以自动转为 URL型的
		
		model.put("doloadstatus","http://"+ipaddress+":"+ipport+"/rest/downloadbeok?vediotimestamp="+nowtime.toString());
		
		//System.out.println("liveUrI:"+liveUrI);//liveUrI:http://43.224.208.195/live/coship,TWSX1422595673115099.m3u8?fmt=x264_0k_mpegts
		//String liveUrL = URItool.URI2URL(liveUrI);
		//System.out.println("liveUrL:"+liveUrL);
		if("".equals(liveUrL) ){
			//地址转化出现问题  异常
			return null;  
		}else{
			
	        return new Callable<String>() {  
	            public String call() throws Exception {  
	            	String uuids = UUID.randomUUID().toString();	            	
	            	//Thread.sleep(1000L);	            	
	            	DownloadThread dt = new DownloadThread(liveUrL, srcurl, tsname_length, tiemlengthi,uuids, nowtime,tspojoDao, tslocalstatuspojoDao);//new出来的 里面的对象不能被注入进来
	            	dt.start();	            	
//	            	downloadThread.setM3u8url(liveUrL);
//	            	downloadThread.setSrcurl(srcurl);
//	            	downloadThread.setTsname_length(tsname_length);
//	            	downloadThread.setTimelength(tiemlengthi);
//	            	downloadThread.setUuids(uuids);
//	            	downloadThread.start();
	        		//m.m3u8download(liveUrL,srcurl,tsname_length,tiemlengthi,uuids);
	                //Thread.sleep(3000L);	            	
	                return "inputtitle";  
	            }  
	        };  
			
		}
	}
	
	
	@RequestMapping("/toplayvedio")
	public  String playm3u8(Map<String, Object> model, HttpServletRequest request) {
		String vediotimestamp =  request.getParameter("vediotimestamp") ;
		String title = request.getParameter("title") ;
		System.out.println("vediotimestamp:"+vediotimestamp+"   -----     title:"+title);
		model.put("m3u8str","http://"+ipaddress+":"+ipport+"/livex/TJ2-800-vedioclip.m3u8?timelength=60&vediotimestamp="+vediotimestamp);
		model.put("vediotitle",title);
		return "playvedio";  
	}
	
	
	
	/**
	 * 方法1 
	 * 1. 剪辑串   （与3联合使用）
	 * 
	 * 生成 m3u8 串 
	 * 	http://127.0.0.1:8080/livex/TJ2-800-vedioclip.m3u8?timelength=60&vediotimestamp=1464167449
	 * 																				   1462331693523
	 * 
	 *  需要截取的时间戳  timestamp
	 *  需要截取的时长      timelength  X秒 
	 *  
	 *  @param 
	 *  	
	 *  	tiemlength 时长 精确到秒
	 * 
	 * */

	@RequestMapping("/TJ2-800-vedioclip.m3u8")
	public String 	productm3u8(Map<String, Object> model, HttpServletRequest request) {	 
		String timestamp = request.getParameter("vediotimestamp") ;		
		Long timestampl = Long.parseLong(timestamp.trim()) ; //精确到秒
		
		Integer iii=timestampl.intValue()/10;
		iii=iii*10;
		//System.out.println("iii:"+iii);
		Long iiil = iii.longValue();
		Long cha = 0L ;
		if(iiil<= timestampl){
			cha =timestampl-iiil;
		}
		Long tspojo_tstimesecond_start=0L; //找到 间隔的区间。startttime
		Long tspojo_tstimesecond_end=0L; //找到 间隔的区间。endttime
		//System.out.println("cha=="+cha);
		if (cha >=ts_per_time.longValue()){
			tspojo_tstimesecond_end =iiil+ts_per_time.longValue();
		}else{
			tspojo_tstimesecond_end =iiil;
		}
		//获取时间间隔
		String timelength =  request.getParameter("timelength") ;
		Integer  tiemlengthi = Integer.parseInt(timelength);		
		int tscount = tiemlengthi/ts_per_time;		 
		tspojo_tstimesecond_start = tspojo_tstimesecond_end- tiemlengthi;	
		System.out.println(tspojo_tstimesecond_start + "------------"+tspojo_tstimesecond_end);
		List<Tspojo> tspojos= tspojoDao.findByTstimesecondBetweenOrderByIdAsc(tspojo_tstimesecond_start, tspojo_tstimesecond_end);
		Iterator<Tspojo> tspojosi = tspojos.iterator();
		String pageReturnStr4 = "" ;		 
		String pageReturnStr4_part2 = "" ;
		//依照 test1vs 的拼串方式
		pageReturnStr4= "#EXTM3U"+lineSeparator
				+"#EXT-X-VERSION:3"+lineSeparator
				+"#EXT-X-TARGETDURATION:7"+lineSeparator
				+"#EXT-X-MEDIA-SEQUENCE:0"+lineSeparator;  

		while(tspojosi.hasNext()){		
			Tspojo t = tspojosi.next();
			pageReturnStr4_part2=pageReturnStr4_part2+"#EXTINF:5,"+lineSeparator
					+"/live/live2/TJ2/800/"+t.getName()+lineSeparator;			
		}
		
		System.out.println("http://"+ipaddress+":"+ipport+"/live/TJ2-800-vedioclip.m3u8?timelength="+timelength+"&timestamp="+timestamp);
		pageReturnStr4= pageReturnStr4+pageReturnStr4_part2
				+"#EXT-X-DISCONTINUITY"+lineSeparator
				+"#EXT-X-ENDLIST"+lineSeparator;	
		//pageReturnStr= pageReturnStr.substring(0, pageReturnStr.length()-lineSeparatorlen);
		System.out.println("--------------------------------------");
		System.out.println(pageReturnStr4);
		System.out.println("--------------------------------------");
		model.put("pageReturnStr", pageReturnStr4);		
		
		return "TJ2800vedioclip";
	}
	
	
	
	
	
	
	
	
	/**
	 * 未用   体验不好
	 *  
	 * 1. 同步下载   单线程下载
	 * 
	 * 生成 m3u8 串 
http://127.0.0.1:8080/livex/liveclip2?liveUrl=http%3A%2F%2F43.224.208.195%2Flive%2Fcoship%2CTWSX1422595673115099.m3u8%3Ffmt%3Dx264_0k_mpegts&timelength=60
	 * 
	 *  
	 *  需要截取的时长      timelength  X秒 
	 *  
	 *  @param 
	 *  	tiemlength 时长 精确到秒
	 *  	liveUrl 直播地址  URI形式 需要变为URL型 
	 * 		---http%3A%2F%2F43.224.208.195%2Flive%2Fcoship%2CTWSX1422595673115099.m3u8%3Ffmt%3Dx264_0k_mpegts
	 * 
	 * 
	 * */
	@RequestMapping("/liveclip2")
	public  String productVedioClip2(Map<String, Object> model, HttpServletRequest request) {	 
		 
		//获取时间间隔
		String timelength =  request.getParameter("timelength") ;
		Integer  tiemlengthi = Integer.parseInt(timelength);	
		String liveUrL = request.getParameter("liveUrl") ;//URI可以自动转为 URL型的
		Long nowtime =  System.currentTimeMillis()/1000 ;//精确到秒
		if("".equals(liveUrL) ){
			//地址转化出现问题  异常
			return null;  
		}else{
			String uuids = UUID.randomUUID().toString();			
			try {
				m3u8Download.m3u8download(liveUrL,srcurl,tsname_length,tiemlengthi,uuids,nowtime,tspojoDao,tslocalstatuspojoDao);
			} catch (ParseException e) {				
				e.printStackTrace();
			}
			return "inputtitle";  		
		}
	}
	
	
		
	/**
	 * 2. 直播串    (存在问题)
	 * 
	 * 生成 m3u8 串 
	 * 	http://127.0.0.1:8080/live/TJ2-800-node1.m3u8?timelength=30&timestamp=1462331549523
	 * 
	 *  需要截取的时间戳  timestamp
	 *  需要截取的时长      timelength  X秒 
	 *  
	 *  @param 
	 *  	timestamp 时间戳 形如 1461918177000   -- 通过 test 中的com.cc.testTimestampe生成
	 *  	tiemlength 时长 精确到秒
	 * 
	 * */

	@RequestMapping("/TJ2-800-node1.m3u8")
	public String 	productVedio(Map<String, Object> model, HttpServletRequest request) {

		System.out.println("------------===============0000000"); //	
		String timestamp = request.getParameter("timestamp") ;		
		Long timestampl = DataConvertTools.TimestampStringToTimestampLong(timestamp) ; //精确到秒
		Integer iii=timestampl.intValue()/10;
		iii=iii*10;
		System.out.println("iii:"+iii);
		Long iiil = iii.longValue();
		Long cha = 0L ;
		if(iiil<= timestampl){
			cha =timestampl-iiil;
		}
		Long tspojo_tstimesecond_start=0L; //找到 间隔的区间。startttime
		Long tspojo_tstimesecond_end=0L; //找到 间隔的区间。endttime
		System.out.println("cha=="+cha);
		if (cha >=ts_per_time.longValue()){
			tspojo_tstimesecond_start=iiil+ts_per_time.longValue();
		}else{
			tspojo_tstimesecond_start=iiil;
		}
		//获取时间间隔
		String timelength =  request.getParameter("timelength") ;
		Integer  tiemlengthi = Integer.parseInt(timelength);		
		int tscount = tiemlengthi/ts_per_time;		 
		tspojo_tstimesecond_end = tspojo_tstimesecond_start+ tiemlengthi;	
		System.out.println(tspojo_tstimesecond_start + "------------"+tspojo_tstimesecond_end);
		List<Tspojo> tspojos= tspojoDao.findByTstimesecondBetweenOrderByIdAsc(tspojo_tstimesecond_start, tspojo_tstimesecond_end);
		Iterator<Tspojo> tspojosi = tspojos.iterator();
		String pageReturnStr = "" ;
		String pageReturnStr_part1 = "" ;
		String pageReturnStr_part2 = "" ;
		pageReturnStr= "#EXTM3U"+lineSeparator+"#EXT-X-TARGETDURATION:"+(ts_per_time+2)+lineSeparator;  //#EXT-X-TARGETDURATION：指定最大的媒体段时间长（秒）。所以#EXTINF中指定的时间长度必须小于或是等于这个最大
		
		/**
		 * #EXTM3U  (源文件中有,此处不再添加字符串)
		 * #EXT-X-TARGETDURATION:5
		 * #EXT-X-MEDIA-SEQUENCE:1460075659
		 * #EXTINF:5,
		 * /live/live2/TJ2/800/TJ2-800-node1_20160429104911_1460075659.ts  #EXTINF:<duration>,<title>  ： duration表示持续的时间（秒）
		 * #EXTINF:5,
		 * /live/live2/TJ2/800/TJ2-800-node1_20160429104916_1460075660.ts
		 * #EXTINF:5,
		 * /live/live2/TJ2/800/TJ2-800-node1_20160429104921_1460075661.ts
		 * #EXTINF:5,
		 * /live/live2/TJ2/800/TJ2-800-node1_20160429104926_1460075662.ts
		 * #EXTINF:5,
		 * /live/live2/TJ2/800/TJ2-800-node1_20160429104931_1460075663.ts 
		 * 
		 * */		
		
		
		int i = 0 ;
		while(tspojosi.hasNext()){		
			Tspojo t = tspojosi.next();
			if(i==0){
				pageReturnStr_part1 ="#EXT-X-MEDIA-SEQUENCE:"+t.getTssequence()+lineSeparator;
			}
			pageReturnStr_part2=pageReturnStr_part2+ "#EXTINF:"+ts_per_time+","+lineSeparator;
			pageReturnStr_part2=pageReturnStr_part2+"/live/live2/TJ2/800/"+t.getName()+lineSeparator;
			/**
			 * #EXTINF:5,
			 * /live/live2/TJ2/800/TJ2-800-node1_20160429104911_1460075659.ts  #EXTINF:<duration>,<title>  ： duration表示持续的时间（秒）
			 * 
			 * */			 
			//System.out.println(t.getId()+"--------------+"+t.getName()+" ========= "+t.getTstimestamp());
			i++;
		}
		
		System.out.println("http://"+ipaddress+":"+ipport+"/live/TJ2-800-node1.m3u8?timelength="+timelength+"&timestamp="+timestamp);
		pageReturnStr= pageReturnStr+pageReturnStr_part1+pageReturnStr_part2;	
		//pageReturnStr= pageReturnStr.substring(0, pageReturnStr.length()-lineSeparatorlen);
		System.out.println("--------------------------------------");
		System.out.println(pageReturnStr);
		System.out.println("--------------------------------------");
		model.put("pageReturnStr", pageReturnStr);
		
		
		return "TJ2800node1";
	}
	
	
	/**
	 * 3. 剪辑外套串
	 * 剪辑后的外套串  与方法1( productVedioClip )一起用 ，套在1的外面
	 * 
	 * http://127.0.0.1:8080/live/INDEX.m3u8?timelength=30&timestamp=1462331693523
	 * 
	 * <video id="player" controls="" webkit-playsinline="" autoplay="" 
	 *  type="m3u8" poster="http://eshare.vod.otvcloud.com/otv/nyz/share/channel01/6/5a/a/logo.jpg?start=1461737498&amp;len=30" 
	 *  src="http://eshare.vod.otvcloud.com/otv/nyz/share/channel01/6/5a/a/index.m3u8?start=1461737498&amp;len=30">	 *  
	 * </video>
	 * 
	 * #EXTM3U
	 * #EXT-X-STREAM-INF:PROGRAM-ID=1,BANDWIDTH=409600
	 * 400.m3u8?start=1461737498&len=30
	 * #EXT-X-STREAM-INF:PROGRAM-ID=1,BANDWIDTH=716800
	 * 700.m3u8?start=1461737498&len=30
	 * 
	 * 
	 * #EXTM3U
	 * #EXT-X-STREAM-INF:PROGRAM-ID=1,BANDWIDTH=409600
	 * INDEX.m3u8?timelength=30&timestamp=1462328208521
	 * 
	 * */
	@RequestMapping("/INDEX.m3u8")
	public String welcome(Map<String, Object> model, HttpServletRequest request) {
		String timestamp = request.getParameter("timestamp") ;
		String timelength = request.getParameter("timelength") ;
		String pageReturnStr2= "#EXTM3U"+lineSeparator+"#EXT-X-STREAM-INF:PROGRAM-ID=1,BANDWIDTH=409600"+lineSeparator
							+"TJ2-800-vedioclip.m3u8?timelength="+timelength+"&timestamp="+timestamp+lineSeparator+
							"#EXT-X-ENDLIST"+lineSeparator;
		//String pageReturnStr2= "#EXTM3U"+lineSeparator+"#EXT-X-STREAM-INF:PROGRAM-ID=1,BANDWIDTH=409600"+lineSeparator
		//		+"Test1.m3u8?timelength="+timelength+"&timestamp="+timestamp+lineSeparator+
		//		"#EXT-X-ENDLIST"+lineSeparator;
		model.put("pageReturnStr", pageReturnStr2);
		return "indexCutVedio";
	}
	
	
	/**
	 * 剪辑测试串
	 * http://127.0.0.1:8080/live/Test1.m3u8
	 * 
	 * 视频截取后的测试片断，可以完全播放 OK
	 * 
	 * 
	 * ---------------------------------
	 * #EXTM3U
	 * #EXT-X-VERSION:3
	 * #EXT-X-TARGETDURATION:7
	 * #EXT-X-MEDIA-SEQUENCE:0
	 * #EXTINF:5,
	 * /live/live2/TJ2/800/TJ2-800-node1_20160504110643_1460162271.ts
	 * #EXTINF:5,
	 * /live/live2/TJ2/800/TJ2-800-node1_20160504110648_1460162272.ts
	 * #EXTINF:5,
	 * /live/live2/TJ2/800/TJ2-800-node1_20160504110653_1460162273.ts
	 * #EXTINF:5,
	 * /live/live2/TJ2/800/TJ2-800-node1_20160504110658_1460162274.ts
	 * #EXTINF:5,
	 * /live/live2/TJ2/800/TJ2-800-node1_20160504110703_1460162275.ts
	 * #EXTINF:5,
	 * /live/live2/TJ2/800/TJ2-800-node1_20160504110708_1460162276.ts
	 * #EXTINF:5,
	 * /live/live2/TJ2/800/TJ2-800-node1_20160504110713_1460162277.ts
	 * #EXTINF:5,
	 * /live/live2/TJ2/800/TJ2-800-node1_20160504110718_1460162278.ts
	 * #EXT-X-DISCONTINUITY
	 * #EXT-X-ENDLIST
	 * ----------------------------------
	 * 
	 * */
	@RequestMapping("/Test1.m3u8")
	public String test1vs(Map<String, Object> model, HttpServletRequest request) {
		String timestamp = request.getParameter("timestamp") ;
		String timelength = request.getParameter("timelength") ;
		String pageReturnStr3= "#EXTM3U"+lineSeparator
		+"#EXT-X-VERSION:3"+lineSeparator
		+"#EXT-X-TARGETDURATION:7"+lineSeparator
		+"#EXT-X-MEDIA-SEQUENCE:0"+lineSeparator
		+"#EXTINF:5,"+lineSeparator
		+"/live/live2/TJ2/800/TJ2-800-node1_20160504110643_1460162271.ts"+lineSeparator
		+"#EXTINF:5,"+lineSeparator
		+"/live/live2/TJ2/800/TJ2-800-node1_20160504110648_1460162272.ts"+lineSeparator
		+"#EXTINF:5,"+lineSeparator
		+"/live/live2/TJ2/800/TJ2-800-node1_20160504110653_1460162273.ts"+lineSeparator
		+"#EXTINF:5,"+lineSeparator
		+"/live/live2/TJ2/800/TJ2-800-node1_20160504110658_1460162274.ts"+lineSeparator
		+"#EXTINF:5,"+lineSeparator
		+"/live/live2/TJ2/800/TJ2-800-node1_20160504110703_1460162275.ts"+lineSeparator
		+"#EXTINF:5,"+lineSeparator
		+"/live/live2/TJ2/800/TJ2-800-node1_20160504110708_1460162276.ts"+lineSeparator
		+"#EXTINF:5,"+lineSeparator
		+"/live/live2/TJ2/800/TJ2-800-node1_20160504110713_1460162277.ts"+lineSeparator
		+"#EXTINF:5,"+lineSeparator
		+"/live/live2/TJ2/800/TJ2-800-node1_20160504110718_1460162278.ts"+lineSeparator		
		+"#EXT-X-DISCONTINUITY"+lineSeparator
		+"#EXT-X-ENDLIST"+lineSeparator;
		System.out.println("/******************** 可以正常播放的截取片段： *************************** ");
		System.out.println(pageReturnStr3);
		System.out.println("/******************** end *************************** ");
		model.put("pageReturnStr", pageReturnStr3);
		return "test1vs";
	}

}
