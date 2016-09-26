package com.cc.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.cc.entity.Tspojo;
 



 

@Transactional
public interface TspojoDao extends CrudRepository<Tspojo, Long>{
	
	
	  public  List<Tspojo>  findByName (String name);
	
	  //@Query("select  distinct(tspojo) from Tspojo tspojo where tspojo.tstimesecond between ?1  and ?2  order by tspojo.id")	
//	  select max(tspojo.id) as id, tspojo.downloadurl as downloadurl, tspojo.name as name,
//	  tspojo.pindaostr as pindaostr, tspojo.tsdatetime as tsdatetime, tspojo.tsexiststatus as tsexiststatus,
//	  tspojo.tssequence as tssequence, tspojo.tsstatus as tsstatus, tspojo.tstimesecond as tstimesecond,
//	  tspojo.tstimestamp as tstimestamp from tspojo tspojo group by tspojo.name  order by tspojo.id
	  //group by  tspojo.name

//SQl语句 --
//--- select tspojo0_.id as id1_2_, tspojo0_.downloadurl as download2_2_, tspojo0_.name as name3_2_,
//--- 	  tspojo0_.pindaostr as pindaost4_2_, tspojo0_.tsdatetime as tsdateti5_2_, tspojo0_.tsexiststatus 
//---	  as tsexists6_2_, tspojo0_.tssequence as tssequen7_2_, tspojo0_.tsstatus as tsstatus8_2_, 
//---	  tspojo0_.tstimesecond as tstimese9_2_, tspojo0_.tstimestamp as tstimes10_2_ from tspojo 
//---	  tspojo0_ where tspojo0_.id in (select max(tspojo1_.id) from tspojo tspojo1_ where tspojo1_.tstimesecond 
//---	  between 1465785954 and 1465786019 group by tspojo1_.name) order by tspojo0_.id
	  
	  //@Query("select  t from Tspojo t left join   "
	  //	  		+ " (select max(tspojo.id) as id, tspojo.name as name from Tspojo tspojo where tspojo.tstimesecond between ?1 and ?2 group by tspojo.name  order by id )  "
	  //	  		+ " B on t.id = B.id   order by t.id")
	  
	  @Query("select  t from Tspojo t where t.id in "
  		+ " (select max(tspojo.id) as id from Tspojo tspojo where   tspojo.tstimesecond between ?1 and ?2 and tspojo.pindaostr=?3  group by tspojo.name )  "
  		+ " order by t.id")   //可去重，去除表中重复Tspojo的记录（以name做唯一性判断） 
	  public List<Tspojo> findDistinctNameByTstimesecondBetweenOrderByIdAsc (Long starttime, Long endtime ,String pindaostr); 
}
