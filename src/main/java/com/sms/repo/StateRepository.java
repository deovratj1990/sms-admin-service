package com.sms.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sms.domain.State;

public interface StateRepository extends CrudRepository<State, Integer>{
 
	public List<State> findByCountryIdOrderByStateNameAsc(Integer id);
	
	public State findByStateName(String stateName);

}
