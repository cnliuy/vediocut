package com.cc.dao;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

import com.cc.entity.Tslocalpojo;
import com.cc.entity.Tspojo;
 



 

@Transactional
public interface TslocalpojoDao extends CrudRepository<Tslocalpojo, Long>{
	
	
	  public Tslocalpojo findByName (String name);
	  
	  
	  public List<Tslocalpojo> findByTstimesecondBetweenOrderByIdAsc (Long starttime, Long endtime); 
}
