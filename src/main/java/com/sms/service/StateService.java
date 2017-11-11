package com.sms.service;

import java.util.List;

import com.sms.domain.State;

public interface StateService {
	
	public List<State> getStateByCountryId(Long id);
	public State saveState(State state);
	
}
