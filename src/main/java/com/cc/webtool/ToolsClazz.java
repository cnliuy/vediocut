package com.cc.webtool;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.cc.dao.TvchannelpindaopojoDao;
import com.cc.entity.Tvchannelpindaopojo;

public  class ToolsClazz {
	
	
 
	/**
	 * 默认的频道是  TJ2-800-node1
	 * 
	 *  具体根据 tvchannelpindaopojo表中的对应关系
	 * 
	 * ---静态类只能传参 不能ioc 注入
	 * */
	public static String GogetPindaoStr(String vediochannel ,TvchannelpindaopojoDao tvchannelpindaopojoDao) {
		System.out.println(":::::::::::::::: in ToolsClazz GogetPindaoStr()");
		String pindaoStr = "TJ2-800-node1"; //默认一个频道
	 
		if(vediochannel==null ||"".equals(vediochannel.trim()) ){			
			
		}else{
			Tvchannelpindaopojo  t = tvchannelpindaopojoDao.findByVediochannel(vediochannel);
			if(t== null){
				
			}else{
				pindaoStr = t.getPindaostr();
				System.out.println(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;查出来了 ------"+pindaoStr);
			}
		}
		
		return pindaoStr;
	}
}
