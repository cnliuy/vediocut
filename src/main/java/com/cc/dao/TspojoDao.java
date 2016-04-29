package com.cc.dao;

import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import com.cc.entity.Tspojo;
 



 

@Transactional
public interface TspojoDao extends CrudRepository<Tspojo, Long>{
	
	
	  public Tspojo findByName (String name);
}
