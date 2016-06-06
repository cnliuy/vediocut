package com.cc.tools2;

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

import com.cc.dao.TspojoDao;
import com.cc.entity.Tspojo;
import com.cc.tool.download.MutilDownTools;
import com.google.common.collect.Lists;


/**
 * 下载m3u8文件 
 * 将其内容拆条存入数据库 不保存ts的文件
 * */
@Component
public class M3u8DownloadC {     
    
	@Autowired
	private TspojoDao tspojoDao;

	/**
	 * 下载m3u8文件，并将文件中的视频信息存到数据库中，但不
	 * 下载其对应的ts文件，仅下载列表。
	 * 
	 * tsname_length : ts 文件名参数   本例中  TJ2-800-node1_20160429160206_1460079414.ts 为 42
	 * timegap : 截取的视频时长   精确到秒
	 * uuids ：鉴别符号
	 * @throws ParseException 
	 * 
	 * */
    public void m3u8download(String m3u8url ,String srcurl,int tsname_length ) throws ParseException  {
    	//System.out.println("-------------取一次m3u8");
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
	        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//20160429162256
	        Date tmpd1date =df.parse("20010101010101"); 
	        Integer tmpSeqint = 0;
	        //Long timediff = 0L ; //时间差 ts之间的
	        //int seqdiff = 0 ;//序列之间的差	        
	       
	        //String  ts_name_x = ts_download_list.get(0);	         
	        //int stringleng =  ts_name_x.length();
	        
	        //String  ts_name_ok_suffix = ts_name_x.substring(0,stringleng-tsname_length);
	        //System.out.println("---- ts_name_ok_suffix:"+ts_name_ok_suffix);//---- ts_name_ok_suffix:/live/live2/TJ2/800/	        
	        //标准名
	        //String  ts_name_ok = ts_name_x.substring(stringleng-tsname_length, stringleng-3);//ts_name:TJ2-800-node1_20160523161556_1460494311 去掉.ts后的。	        
	        //System.out.println("---- ts_name:"+ts_name_ok);
	        
	        //String tsname_ok_arr[] = ts_name_ok.split("_"); //标准名 ts_name: TJ2-800-node1_20160523161556_1460494311
	        //String suffix_ok_String = tsname_ok_arr[0]; //TJ2-800-node1
	        //String data_ok_String = tsname_ok_arr[1]; //20160523161556
	        //String seq_ok_String = tsname_ok_arr[2]; //1460494311
	        //ts.setName(ts_name_ok+".ts");
	        
	        int noexist = 0 ;
    		int nostatus = 0;
	        String tmpurl ;
	        /***
	         * 得到 ts的明明规则 
	         *  即 得到 时间上的差值 和 序列上的差值 
	         *  timediff
	         *  seqdiff
	         * */
	        for(int i = 0; i <  ts_download_list.size(); i++){ 	   
	        	
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
	        	try {
					tmpd1date = df.parse(tsname_arr[1]);
		        	tmpSeqint = Integer.parseInt(tsname_arr[2].substring(0, tsname_arr[2].length()-3));	        		
		        	System.out.println("tmpSeqint:"+tmpSeqint);//tmpSeqint:1460526735
		        	System.out.println("tmpd1date:"+tmpd1date);
		        	List<Tspojo> tpa = tspojoDao.findByName(tsname);
		        	Tspojo tp ;
		        	if(tpa.size()<1){//tp==null
		        		tp = new Tspojo();		        		
			        	tp.setDownloadurl(tmpurl);
			        	tp.setName(tsname);
			        	tp.setTssequence(tmpSeqint.toString());
			        	tp.setTsdatetime(tsname_arr[1]);
			        	tp.setPindaostr(tsname_arr[0]);
			        	tp.setTsexiststatus(noexist);
			        	tp.setTsstatus(nostatus);
			        	tp.setTstimesecond(tmpd1date.getTime()/1000);//转为秒
			        	tspojoDao.save(tp);
		        	}     	
		        	
				} catch (ParseException e) {						
					e.printStackTrace();
				}
	        	      
	        }
	        
	        
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
