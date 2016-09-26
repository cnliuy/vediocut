package com.cc.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tvchannelpindaopojo")
public class Tvchannelpindaopojo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;  
	
	private String vediochannel; //传递的频道  对应url中传递的 vediochannel	例子 live_live2_TJ2_800
	
	private String pindaostr; //频道的  TJ2-800-node1 对应  Tspojo pindaostr

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVediochannel() {
		return vediochannel;
	}

	public void setVediochannel(String vediochannel) {
		this.vediochannel = vediochannel;
	}

	public String getPindaostr() {
		return pindaostr;
	}

	public void setPindaostr(String pindaostr) {
		this.pindaostr = pindaostr;
	}
	
	
}
