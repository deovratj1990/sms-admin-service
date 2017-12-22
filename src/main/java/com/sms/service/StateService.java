package com.sms.service;

import java.util.List;

import com.sms.domain.State;

public interface StateService {
	
	public List<State> getByCountryId(Integer countryId);
	
	public State save(State state);
	
	public State getByStateId(Integer stateId);
	
	public State getByStateName(String stateName);
	
}
