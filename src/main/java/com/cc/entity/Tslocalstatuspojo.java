package com.cc.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 某时间点的 m3u8里对应的ts状态是否下载完成
 * 
 * */
@Entity
@Table(name = "tslocalstatuspojo")
public class Tslocalstatuspojo implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;  
	
	
	private Long tstimesecond;//精确到秒
	
	private int tsstat;//状态    0 ,无  ；1 ,ok

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTstimesecond() {
		return tstimesecond;
	}

	public void setTstimesecond(Long tstimesecond) {
		this.tstimesecond = tstimesecond;
	}

	public int getTsstat() {
		return tsstat;
	}

	public void setTsstat(int tsstat) {
		this.tsstat = tsstat;
	}
	
	
	
}
