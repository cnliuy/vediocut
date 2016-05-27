package com.cc.dao;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

import com.cc.entity.Tslocalstatuspojo;
import com.cc.entity.Tspojo;
 



 

@Transactional
public interface TslocalstatuspojoDao extends CrudRepository<Tslocalstatuspojo, Long>{
	
	
	  public Tslocalstatuspojo findByTstimesecond (Long tstimesecond);
	  
	  
	  public List<Tspojo> findByTstimesecondBetween (Long starttime, Long endtime); 
}
