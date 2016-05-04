package com.cc.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "tspojo")
public class Tspojo implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;  
	
	private String name; //全名
	
	private String pindaostr;//频道号
	
	
	private String tssequence;
	
	private String tstimestamp;
	
	private Long tstimesecond;//精确到秒

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

 
 
	
	

}
