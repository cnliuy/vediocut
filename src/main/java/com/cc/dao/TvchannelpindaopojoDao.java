package com.cc.dao;


import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import com.cc.entity.Tvchannelpindaopojo;
 



 

@Transactional
public interface TvchannelpindaopojoDao extends CrudRepository<Tvchannelpindaopojo, Long>{
	
	
	  public Tvchannelpindaopojo findByVediochannel (String vediochannel);
	  
}
