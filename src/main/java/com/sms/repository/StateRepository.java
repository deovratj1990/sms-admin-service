package com.sms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sms.domain.State;

public interface StateRepository extends CrudRepository<State, Long>{
 
	public List<State> findByCountryId(Long id);
	
	@Query(value = "select c.country_name, s.state_id, s.state_name from tbl_state s inner join tbl_country c on s.country_id = c.country_id", nativeQuery = true)
	public List<Object[]> getAll();
	
}
