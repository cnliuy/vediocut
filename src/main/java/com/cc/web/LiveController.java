package com.cc.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cc.dao.TspojoDao;
import com.cc.entity.Tspojo;
import com.cc.tools.DataConvertTools;



@Controller
@RequestMapping("/live")
public class LiveController {
	@Autowired
	private TspojoDao tspojoDao;
	
	private Integer ts_per_time = 5; //ts 视频切片的间隔 5秒
	
	private String  lineSeparator = System.getProperty("line.separator", "/n"); 
	private int  lineSeparatorlen = lineSeparator.length(); 
	
	/**
	 * 直播串
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
		
		System.out.println("http://127.0.0.1:8080/live/TJ2-800-node1.m3u8?timelength="+timelength+"&timestamp="+timestamp);
		pageReturnStr= pageReturnStr+pageReturnStr_part1+pageReturnStr_part2;	
		//pageReturnStr= pageReturnStr.substring(0, pageReturnStr.length()-lineSeparatorlen);
		System.out.println("--------------------------------------");
		System.out.println(pageReturnStr);
		System.out.println("--------------------------------------");
		model.put("pageReturnStr", pageReturnStr);
		
		
		return "TJ2800node1";
	}
	
	
	/**
	 * http://127.0.0.1:8080/live/INDEX.m3u8?timelength=30&timestamp=1462331549523
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
							+"Test1.m3u8?timelength="+timelength+"&timestamp="+timestamp+lineSeparator+
							"#EXT-X-ENDLIST"+lineSeparator;
		model.put("pageReturnStr", pageReturnStr2);
		return "indexCutVedio";
	}
	
	
	/**
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
