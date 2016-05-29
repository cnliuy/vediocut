package com.cc.dao;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import com.cc.entity.Tspojo;
import com.cc.entity.Vediocutpojo;
 



 

@Transactional
public interface VediocutpojoDao extends CrudRepository<Vediocutpojo, Long>{
	
	
	  public Vediocutpojo findByVediocutuuid (String vediocutuuid);
	  
	  
	 
}
