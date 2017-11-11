package com.sms.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sms.domain.State;

public interface StateRepository extends CrudRepository<State, Long>{
 
	public List<State> findByCountryId(Long id);
	
}
