package com.cc.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cc.Someconstant;
import com.cc.dao.TslocalstatuspojoDao;
import com.cc.dao.TspojoDao;
import com.cc.entity.Tslocalstatuspojo;
import com.cc.entity.Tspojo;
import com.cc.tool.download.MutilDownTools;
import com.google.common.collect.Lists;


/**
 * 下载m3u8文件，并保存时长内的ts文件
 * 
 * */
@Component
public class M3u8Download {
	
//	public static void main(String[] args) throws ParseException {
//		String m3u8url="http://43.224.208.195/live/coship,TWSX1422589417980523.m3u8?fmt=x264_0k_mpegts";
//		String srcurl="http://43.224.208.195/";
//		int  tsname_length = 42;
//		M3u8Download m = new M3u8Download();
//		m.m3u8download(m3u8url,srcurl,tsname_length,60,"");
//	}
	
	//@Autowired
	//private TspojoDao tspojoDao;

	
	//public TspojoDao getTspojoDao() {
	//	return tspojoDao;
	//}


	//public void setTspojoDao(TspojoDao tspojoDao) {
	//	this.tspojoDao = tspojoDao;
	//}

	
	
        
    
	

	/**
	 * ---- be used
	 * tsname_length : ts 文件名参数   本例中  TJ2-800-node1_20160429160206_1460079414.ts 为 42
	 * timegap : 截取的视频时长   精确到秒
	 * uuids ：鉴别符号
	 * @throws ParseException 
	 * 
	 * */
    public void m3u8download(String m3u8url ,String srcurl,int tsname_length , int timegap ,String uuids,Long nowtime,
    		TspojoDao tspojoDao,
    		TslocalstatuspojoDao tslocalstatuspojoDao) throws ParseException  {
    	 
    	// 生成一个httpclient对象  
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        HttpGet httpget = new HttpGet(m3u8url);      
        HttpResponse response;
        List<String> ts_download_list = null;
		try {
			response = httpclient.execute(httpget);
	        HttpEntity entity = response.getEntity();  
	        InputStream in = entity.getContent(); 	          
	        ts_download_list = inputStream2StringNoSharp(in);//去掉相应的#
		}catch (IOException e){
			
		}finally{
			try {
				httpclient.close();
			} catch (IOException e) {				
				e.printStackTrace();
			}
		} 
		
		
	    
			if(ts_download_list.size()>0){	        
		       	List<String> in_filenames = new ArrayList<String>();
		       	String tmpurl ;	        
		        
		        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//20160429162256
		        Date tmpd1date =df.parse("20010101010101");
		        int tmpSeqint = 0;
		        Long timediff = 0L ; //时间差 ts之间的
		        int seqdiff = 0 ;//序列之间的差
		        
		       
		        String  ts_name_x = ts_download_list.get(0);	         
		        int stringleng =  ts_name_x.length();
		        
		        String  ts_name_ok_suffix = ts_name_x.substring(0,stringleng-tsname_length);
		        //System.out.println("---- ts_name_ok_suffix:"+ts_name_ok_suffix);//---- ts_name_ok_suffix:/live/live2/TJ2/800/	        
		        //标准名
		        String  ts_name_ok = ts_name_x.substring(stringleng-tsname_length, stringleng-3);//ts_name:TJ2-800-node1_20160523161556_1460494311 去掉.ts后的。	        
		        //System.out.println("---- ts_name:"+ts_name_ok);
		        
		        String tsname_ok_arr[] = ts_name_ok.split("_"); //标准名 ts_name: TJ2-800-node1_20160523161556_1460494311
		        String suffix_ok_String = tsname_ok_arr[0]; //TJ2-800-node1
		        String data_ok_String = tsname_ok_arr[1]; //20160523161556
		        String seq_ok_String = tsname_ok_arr[2]; //1460494311
	
		        /***
		         * 得到 ts的明明规则 
		         *  即 得到 时间上的差值 和 序列上的差值 
		         *  timediff
		         *  seqdiff
		         * */
		        for(int i = 0; i < 2; i++){ 	        	
	        		//System.out.println("srcurl:"+srcurl);//srcurl:http://43.224.208.195/
	        		tmpurl = srcurl+ ts_download_list.get(i);//tmpurl:http://43.224.208.195//live/live2/TJ2/800/TJ2-800-node1_20160523143326_1460493081.ts
	        		//System.out.println("tmpurl:"+tmpurl); //tmpurl:http://43.224.208.195//live/live2/TJ2/800/TJ2-800-node1_20160523160506_1460494181.ts
	        		int endIndex = tmpurl.length();
	        		int beginIndex = endIndex-tsname_length;
	        		String  tsname = tmpurl.substring(beginIndex, endIndex); //示例 TJ2-800-node1_20160429160206_1460079414.ts
	        		//通过下划线 截取字符串
	        		//tsname_arr[0]:TJ2-800-node1
	        		//tsname_arr[1]:20160523144726
	        		//tsname_arr[2]:1460493249.ts
	        		String tsname_arr[] = tsname.split("_");	            
	        		//System.out.println("tsname_arr[0]:"+tsname_arr[0]);
	        		//System.out.println("tsname_arr[1]:"+tsname_arr[1]);
	        		//System.out.println("tsname_arr[2]:"+tsname_arr[2]);	        	
		        	if( i == 0) {
		        		try {
							tmpd1date = df.parse(tsname_arr[1]);
			        		tmpSeqint = Integer.parseInt(tsname_arr[2].substring(0, tsname_arr[2].length()-3));	        		
			        		//System.out.println("tmpSeqint:"+tmpSeqint);
			        		//System.out.println("tmpd1date:"+tmpd1date);
						} catch (ParseException e) {						
							e.printStackTrace();
						}
		        	}	        	
		        	if( i == 1) {
		        		try {
							timediff = (df.parse(tsname_arr[1])).getTime() - tmpd1date.getTime() ;
			        		seqdiff = Integer.parseInt(tsname_arr[2].substring(0, tsname_arr[2].length()-3)) - tmpSeqint;		        		
			        		System.out.println("时间差 timediff:"+timediff);
			        		System.out.println("序列差 seqdiff:"+seqdiff);
						} catch (ParseException e) {
							e.printStackTrace();
						}	
		        	}             
		        }
		        
		        Long timediff_s = timediff/1000; //转为秒
		        int  timediff_s_i = timediff_s.intValue();
		        int ts_number = timegap /timediff_s_i  ; //需要ts 切片的个数 
		        //System.out.println("ts_number:"+ts_number);  12	 
		        /**
		         * 生成需要下载的文件列表名
		         * */
		        String  tmpTsname ;
		        ArrayList <Tspojo> tspojo_need_download = new ArrayList<Tspojo>();	        
		        for( int j = 0 ; j < ts_number ;j++){
		        	Tspojo tp = new Tspojo();
		        	Long tmpdatal = df.parse(data_ok_String).getTime()- timediff*j ;
		        	Date tmpdate = new Date(tmpdatal);
		        	String tmpdataString = df.format(tmpdate);	        	
		        	Integer tmpseqi = Integer.parseInt(seq_ok_String)-seqdiff*j;
		        	//System.out.println("--------"+tmpdataString);//20160523170146
		        	//System.out.println("-----------"+tmpseqi);//1460494861	        
		        	//System.out.println("----ts name----"+ ts_name_ok_suffix+suffix_ok_String+"_"+tmpdataString+"_"+tmpseqi+".ts");	
		        	//----ts name----/live/live2/TJ2/800/TJ2-800-node1_20160523170911_1460494950.ts
		        	String tmptsname = suffix_ok_String+"_"+tmpdataString+"_"+tmpseqi+".ts";
		        	String tmpurl_ =  srcurl+ts_name_ok_suffix+tmptsname;
		        	//System.out.println("----ts url----"+ tmpurl_);//----ts url----http://43.224.208.195//live/live2/TJ2/800/TJ2-800-node1_20160523171151_1460494982.ts	
		        	tp.setDownloadurl(tmpurl_);
		        	tp.setName(tmptsname);
		        	tp.setTssequence(tmpseqi.toString());
		        	tp.setTsdatetime(tmpdataString);
		        	tp.setPindaostr(suffix_ok_String);
		        	tspojo_need_download.add(tp);	        	
		        }
		        
		        /**
		         * 获取ts下载列表  为倒序形式的。
		         * */
		        //List <Tspojo> returntslist = new ArrayList<Tspojo>();
		        List <Tspojo> new_tspojo_need_download = Lists.reverse(tspojo_need_download);
		        
		        for(int k=0 ; k< new_tspojo_need_download.size();k++){	        	
		        	String destfilePath = M3u8Download.GoGetFileSavePath();
		        	Tspojo tsp = new_tspojo_need_download.get(k);	
		        	List <Tspojo> oldtsp = tspojoDao.findByName(tsp.getName());
		        	System.out.println("下载文件为----"+tsp.getName());
		        	if(oldtsp.size() <1 ){	//oldtsp == null        		
		        		//新文件需要下载 
			        	//下载保存文件
						String oofilename = MutilDownTools.MutilDown(Someconstant.download_threads, destfilePath+File.separator+tsp.getName(),tsp.getDownloadurl() ); //下载完成               
						if("".equals(oofilename)){
							//出现了问题 不做保存
						}else{
							Long tstimesecond = DataConvertTools.DataStringToTimestampLong(tsp.getTsdatetime());
							tsp.setTstimesecond(tstimesecond); 	
							//在下载完成之后，再核查一次数据库中是否存在了对象  下载的时候也需要时间，期间可能有新对象产生
							List <Tspojo>  sectcp = tspojoDao.findByName(tsp.getName()) ;
							if(sectcp.size() == 0){	  //sectcp == null
								tspojoDao.save(tsp);  //将数据保存到数据库
							}
							//System.out.println("----------"+tsp.getName());
						}
		        	}
		        }	       
			}
			System.out.println("======================== 下载完成 =====================");
			List <Tslocalstatuspojo> tslocalstatuspojo = tslocalstatuspojoDao.findByTstimesecond(nowtime) ;
			if( tslocalstatuspojo.size()==0 ||tslocalstatuspojo == null ){
				Tslocalstatuspojo  tlsp = new Tslocalstatuspojo();
				tlsp.setTstimesecond(nowtime);
				tlsp.setTsstat(1);
				tslocalstatuspojoDao.save(tlsp);
			}
		
    	}
    
    
    
    
        
        
    /**
     * 过滤掉#
     * 
     * */
	public	static List<String>	inputStream2StringNoSharp(InputStream	in)	throws	IOException	{ 
       
            BufferedReader in2=new BufferedReader(new InputStreamReader(in));
            String y="";
            List<String> list = new ArrayList<String> ();
            while((y=in2.readLine())!=null){
            	//一行一行读     
            	if(y.contains("#")){
            		System.out.println(y);
            	}else{
            		System.out.println("List---"+y);
            		list.add(y);
            	}
            	
            }
            return list;        
	} 
        
        
	public static String GoGetFileSavePath(){
        	//下载后切片放置的目录
        	String destFilePath="F:"+File.separator+"Java2016"+File.separator+"src"+File.separator+"vediocut"+
        							File.separator+"vediocut"+File.separator+"src"+File.separator+"main"+
        							File.separator+"resources"+File.separator+"static"+File.separator+"live"
        							+File.separator+"live2"+File.separator+"TJ2"+File.separator+"800";   
        	
        	
        	//F:\Java2016\src\vediocut\vediocut\src\main\webapp\live\live2\TJ2\800
        	destFilePath="F:"+File.separator+"Java2016"+File.separator+"src"+File.separator+"vediocut"+
					File.separator+"vediocut"+File.separator+"src"+File.separator+"main"+
					File.separator+"webapp"+File.separator+"live"
					+File.separator+"live2"+File.separator+"TJ2"+File.separator+"800"; 
        	
        	//---服务器的地址
        	//---C:\Java\vediosrc_git\vediocut\src\main\webapp\live\live2\TJ2\800
//        	destFilePath="C:"+File.separator+"Java"+File.separator+"vediosrc_git"+File.separator+"vediocut"
//        			+File.separator+"src"+File.separator+"main"+
//					File.separator+"webapp"+File.separator+"live"
//					+File.separator+"live2"+File.separator+"TJ2"+File.separator+"800"; 
        	
        	try {
        		if (!(new File(destFilePath).isDirectory())) {
        			new File(destFilePath).mkdir();
        		}
        	} catch (SecurityException e) {
        		   e.printStackTrace();
        	}
        	System.out.println("目录path："+destFilePath);
        	return destFilePath ;
        	
	}



        
        
        
}
