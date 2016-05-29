package com.cc.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * vediocut视频片断对象
 * 
 * */
@Entity
@Table(name = "vediocutpojo")
public class Vediocutpojo implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;  
	
	
	private String vediocutuuid; 
	
	private String vediocuturl; //链接
	
	private String vediocuttitle; //名称
	
	private Integer vediocutlen;//时长

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVediocutuuid() {
		return vediocutuuid;
	}

	public void setVediocutuuid(String vediocutuuid) {
		this.vediocutuuid = vediocutuuid;
	}

	public String getVediocuturl() {
		return vediocuturl;
	}

	public void setVediocuturl(String vediocuturl) {
		this.vediocuturl = vediocuturl;
	}

	public String getVediocuttitle() {
		return vediocuttitle;
	}

	public void setVediocuttitle(String vediocuttitle) {
		this.vediocuttitle = vediocuttitle;
	}

	public Integer getVediocutlen() {
		return vediocutlen;
	}

	public void setVediocutlen(Integer vediocutlen) {
		this.vediocutlen = vediocutlen;
	}

	
	
	
}
