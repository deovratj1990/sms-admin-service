package com.sms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sms.domain.State;

public interface StateRepository extends CrudRepository<State, Integer>{
 
	public List<State> findByCountryIdOrderByStateNameAsc(Integer id);
	
	@Query(value = "select c.country_name, s.state_id, s.state_name from state s inner join country c on s.country_id = c.country_id", nativeQuery = true)
	public List<Object[]> getAll();
	
}
