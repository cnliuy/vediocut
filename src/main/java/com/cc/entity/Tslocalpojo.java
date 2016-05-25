package com.cc.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 本地存储ts的 数据信息
 * 
 * */
@Entity
@Table(name = "tslocalpojo")
public class Tslocalpojo implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;  
	
	private String name; //全名	
	
	private String downloadurl; //下载全链接
	
	private String pindaostr;//频道号
	private String tsdatetime;//精确到秒  yyyyMMddHHmmss
	
	private String tssequence;
	
	private String tstimestamp;
	
	private Long tstimesecond;//精确到秒
	
	private int  tsexiststatus; //ts状态     0 本地不存在  ；   1已下载   存在
	
	private int  tsstatus; //ts状态  被使用 与否 

	public String getTstimestamp() {
		return tstimestamp;
	}

	public void setTstimestamp(String tstimestamp) {
		this.tstimestamp = tstimestamp;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTssequence() {
		return tssequence;
	}

	public void setTssequence(String tssequence) {
		this.tssequence = tssequence;
	}

	public String getPindaostr() {
		return pindaostr;
	}

	public void setPindaostr(String pindaostr) {
		this.pindaostr = pindaostr;
	}

	public Long getTstimesecond() {
		return tstimesecond;
	}

	public void setTstimesecond(Long tstimesecond) {
		this.tstimesecond = tstimesecond;
	}

	public String getDownloadurl() {
		return downloadurl;
	}

	public void setDownloadurl(String downloadurl) {
		this.downloadurl = downloadurl;
	}

	public String getTsdatetime() {
		return tsdatetime;
	}

	public void setTsdatetime(String tsdatetime) {
		this.tsdatetime = tsdatetime;
	}

	public int getTsstatus() {
		return tsstatus;
	}

	public void setTsstatus(int tsstatus) {
		this.tsstatus = tsstatus;
	}

	public int getTsexiststatus() {
		return tsexiststatus;
	}

	public void setTsexiststatus(int tsexiststatus) {
		this.tsexiststatus = tsexiststatus;
	}

 
 
	
	

}
