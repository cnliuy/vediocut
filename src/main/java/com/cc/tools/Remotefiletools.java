package com.cc.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cc.dao.TspojoDao;
import com.cc.entity.Tspojo;
import com.cc.tool.download.MutilDownTools;

/**
 * 参考 http://zhangzhaoaaa.iteye.com/blog/2200186
 * httpclient4.3 上传下载文件
 * 
 * */
@Component
public class Remotefiletools {
	 
	@Autowired
	private TspojoDao tspojoDao;
	  
    /**
     * 下载文件测试
     * 
     * url: http://43.224.208.195/live/coship,TWSX1422589417980523.m3u8?fmt=x264_0k_mpegts
     * destFileName: xxx.m3u8
     *    并将其合并为 一个大的TS文件
     *    
     *    timestamp=1461833911464
     * */
    public static void main_mm(String[] args) throws ClassNotFoundException { 
    	long s = System.nanoTime();//---计时器开始
    	String url ="http://43.224.208.195/live/coship,TWSX1422589417980523.m3u8?fmt=x264_0k_mpegts";
    	String srcurl ="http://43.224.208.195/";
    	String destFileName="xxx.m3u8";
    	//下载后切片放置的目录
    	String destFilePath="F:"+File.separator+"Java2016"+File.separator+"src"+File.separator+"vediocut"+
    							File.separator+"vediocut"+File.separator+"src"+File.separator+"main"+
    							File.separator+"resources"+File.separator+"static"+File.separator+"live"
    							+File.separator+"live2"+File.separator+"TJ2"+File.separator+"800";   
    	//F:\Java2016\src\vediocut\vediocut\src\main\resources\static\tsvedio
    	//F:\Java2016\src\vediocut\vediocut\vedios2\
    	try {
    		if (!(new File(destFilePath).isDirectory())) {
    			new File(destFilePath).mkdir();
    		}
    	} catch (SecurityException e) {
    		   e.printStackTrace();
    	}
    	System.out.println("目录path："+destFilePath);
    	System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
    	System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
    	System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient", "stdout");
    	UUID uuid = UUID.randomUUID();
    	//合成后的文件目录和名称  全
    	String compound_destFile =  destFilePath+File.separator +uuid+".ts"; 
    	List<String>  spiltFiles ;
        try {  
        	//spiltFiles = getM3u8( url,srcurl  ,destFilePath ); 
        	Remotefiletools r = new Remotefiletools();
        	spiltFiles = r.getM3u8_DownFiles( url,srcurl  ,destFilePath );
        	
        	//spiltFiles = getM3u8( url,srcurl ,destFileName ,destFilePath ); 
        	/**
        	 * 合并ts的切片文件
        	 * 
        	 * */
//        	if (spiltFiles==null || spiltFiles.size()==0){
//        		
//        	}else{        		
//        		try {
//					CopyBFiles.combineFile2(spiltFiles, compound_destFile);
//				} catch (Exception e) {					
//					e.printStackTrace();
//				} 
//        	}
        } catch (ParseException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        
        long d = System.nanoTime() - s;//---计时器结束
		System.out.println("花费时间 ="+(d/1000000)+"毫秒");
    }
	
	
	
	/** 
     * 下载文件 
     *  
     * @return   返回值 示例 String  文件全路径（包括文件名）集合   :F:\Java2016\src\vediocut\vediocut\src\main\resources\static\live\live2\TJ2\800\TJ2-800-node1_20160429160201_1460079413.ts
     * @throws ClientProtocolException 
     * @throws IOException 
     */  
    public static List<String> getM3u8(String url, String srcurl ,String destFilePath)  throws ClientProtocolException, IOException {  
    	java.util.logging.Logger.getLogger("org.apache.http.wire").setLevel(java.util.logging.Level.FINEST);
    	java.util.logging.Logger.getLogger("org.apache.http.headers").setLevel(java.util.logging.Level.FINEST);
    	System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
    	System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
    	System.setProperty("org.apache.commons.logging.simplelog.log.httpclient.wire", "ERROR");
    	System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "ERROR");
    	System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.headers", "ERROR");
    	
    	
    	System.out.println("------here-------------");
        // 生成一个httpclient对象  
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        HttpGet httpget = new HttpGet(url);      
        HttpResponse response = httpclient.execute(httpget);  
        HttpEntity entity = response.getEntity();  
        InputStream in = entity.getContent(); 
        List<String> ts_download_list = inputStream2StringNoSharp(in) ;
        //System.out.println("--------------InputStream:"+ inputStream2StringNoSharp(in)); 
    	List<String> in_filenames = new ArrayList<String>();
    	
		
        String tmpurl ;
        for(int i = 0; i < ts_download_list.size(); i++){ 
        	//System.out.println(srcurl);
        	tmpurl = srcurl+ ts_download_list.get(i);  
            System.out.println(tmpurl); 
            int endIndex = tmpurl.length();
            int beginIndex =endIndex-42;
            String  tsname = tmpurl.substring(beginIndex, endIndex);
            System.out.println("tsname:"+tsname); 
            //单个文件的多线程下载 
            String oofilename = MutilDownTools.MutilDown(3, destFilePath+File.separator+tsname,tmpurl );
    		in_filenames.add(oofilename);
    		System.out.println("oofilename:"+oofilename);
            //getFile(tmpurl,destFilePath+"/"+i+".TS");
        } 
        
        httpclient.close();          
        
        return in_filenames ;
 
    }  
    
    
    
    /** 
     * 下载文件 2
     *    ------ 新改的  以此为准  在用 using
     *    功能：
     *    
     *    下载 m3u8文件，并下载相应视频，存入数据库。
     *    重复的ts丢掉，不下载，不存数据库。
     *    
     * @return   返回值 示例 String    只是文件名集合  TJ2-800-node1_20160429160206_1460079414.ts
     * @param url  (url ="http://43.224.208.195/live/coship,TWSX1422589417980523.m3u8?fmt=x264_0k_mpegts";)
     * @throws ClientProtocolException 
     * @throws IOException 
     */  
    public List<String> getM3u8_DownFiles(String url, String srcurl ,String destFilePath)  throws ClientProtocolException, IOException {  
    	java.util.logging.Logger.getLogger("org.apache.http.wire").setLevel(java.util.logging.Level.FINEST);
    	java.util.logging.Logger.getLogger("org.apache.http.headers").setLevel(java.util.logging.Level.FINEST);
    	System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
    	System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
    	System.setProperty("org.apache.commons.logging.simplelog.log.httpclient.wire", "ERROR");
    	System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "ERROR");
    	System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.headers", "ERROR");
    	
    	
    	//System.out.println("------here-------------");
        // 生成一个httpclient对象  
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        HttpGet httpget = new HttpGet(url);      
        HttpResponse response = httpclient.execute(httpget);  
        HttpEntity entity = response.getEntity();  
        InputStream in = entity.getContent(); 
        List<String> ts_download_list = inputStream2StringNoSharp(in) ;
        //System.out.println("--------------InputStream:"+ inputStream2StringNoSharp(in)); 
    	List<String> in_filenames = new ArrayList<String>();
    	
		
        String tmpurl ;
        for(int i = 0; i < ts_download_list.size(); i++){ 
        	//System.out.println(srcurl);
        	tmpurl = srcurl+ ts_download_list.get(i);  
            System.out.println(tmpurl); 
            int endIndex = tmpurl.length();
            int beginIndex =endIndex-42;
            String  tsname = tmpurl.substring(beginIndex, endIndex); //示例 TJ2-800-node1_20160429160206_1460079414.ts
            String  dataString = tsname.substring(14, 28);
            int tsnameendIndex = tsname.length();
            String  seqString = tsname.substring(29,tsnameendIndex-3);
            String  pindaoString = tsname.substring(0, 13);
            System.out.println("tsname:"+tsname);//tsname:TJ2-800-node1_20160429162256_1460079664.ts
            System.out.println("dataString:"+dataString);//dataString:20160429162256
            System.out.println("seqString:"+seqString);//seqString:1460079664
            System.out.println("pindaoString:"+pindaoString);//pindaoString:TJ2-800-node1
            List <Tspojo> tspojo =  tspojoDao.findByName(tsname);
            /**
             * 需事务处理
             * 
             * */
            if (tspojo.size()==0){ //tspojo==null
            	//不存在该文件 进行下载
                //单个文件的多线程下载 
                String oofilename = MutilDownTools.MutilDown(3, destFilePath+File.separator+tsname,tmpurl ); //下载完成               
        		in_filenames.add(tsname);
        		Tspojo tspojo2 =new Tspojo();
        		tspojo2.setName(tsname);
        		tspojo2.setPindaostr(pindaoString);
        		tspojo2.setTssequence(seqString);
        		tspojo2.setTstimestamp(dataString);        		
        		Long tstimesecond = DataConvertTools.DataStringToTimestampLong(dataString);
        		tspojo2.setTstimesecond(tstimesecond); //不成功的话，转换为0L,也存入数据库。       
        		tspojoDao.save(tspojo2);  //将数据保存到数据库       
        		System.out.println("============ 保存数据库成功  tsname:"+tsname); 
            }else{
            	System.out.println("-----------该 tspojo 数据已存在 不做保存 ："+tspojo.get(0).getName());
            }
            
    		//System.out.println("oofilename:"+oofilename);
            //getFile(tmpurl,destFilePath+"/"+i+".TS");
        } 
        
        httpclient.close();          
        
        return in_filenames ;
 
    }  
    
    public static void getFile(String url2,String destFileName)  throws ClientProtocolException, IOException { 
    	 CloseableHttpClient httpclient2 = HttpClients.createDefault();  
         HttpGet httpget2 = new HttpGet(url2);      
         HttpResponse response2 = httpclient2.execute(httpget2);  
         HttpEntity entity2 = response2.getEntity();  
         InputStream in2 = entity2.getContent(); 
    	
    	
         File file = new File(destFileName);  
         try {  
            FileOutputStream fout = new FileOutputStream(file);  
            int l = -1;  
            byte[] tmp = new byte[1024];  
            while ((l = in2.read(tmp)) != -1) {  
                fout.write(tmp, 0, l);  
                //注意这里如果用OutputStream.write(buff)的话，图片会失真，大家可以试试  
            }  
            fout.flush();  
            fout.close();  
         } finally {  
            // 关闭低层流。  
            in2.close();  
         }  
         httpclient2.close(); 
    }
    
    
    
    
    
    /**
     * 不过滤#
     * 
     * */
    public	static String	inputStream2String(InputStream	in)	throws	IOException	{ 
        StringBuffer out= new StringBuffer(); 
        byte[] b= new byte[4096]; 
        
        for(int n;(n = in.read(b)) != -1;) { 
        	out.append(new String(b, 0, n)); 
        } 
        return out.toString(); 
    } 
    
    
    /**
     * 需要过滤#
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
    
    
    
    
  
  
}
