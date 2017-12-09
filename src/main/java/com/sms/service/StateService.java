package com.sms.service;

import java.util.List;
import java.util.Map;

import com.sms.domain.State;

public interface StateService {
	
	public List<Map<String, String>> getAll();
	public List<State> getByCountryId(Long countryId);
	public State save(State state);
	public State getByStateId(Long stateId);
	
}
