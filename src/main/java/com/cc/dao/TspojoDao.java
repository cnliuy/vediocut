package com.cc.dao;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import com.cc.entity.Tspojo;
 



 

@Transactional
public interface TspojoDao extends CrudRepository<Tspojo, Long>{
	
	
	  public  List<Tspojo>  findByName (String name);
	  
	  
	  public List<Tspojo> findDistinctNameByTstimesecondBetweenOrderByIdAsc (Long starttime, Long endtime); 
}
