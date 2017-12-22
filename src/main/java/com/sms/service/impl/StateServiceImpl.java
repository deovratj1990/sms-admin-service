package com.sms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.domain.State;
import com.sms.repo.StateRepository;
import com.sms.service.StateService;

@Service
public class StateServiceImpl implements StateService {

	@Autowired
	private StateRepository stateRepository; 
	
	public List<State> getByCountryId(Integer countryId) {
		return stateRepository.findByCountryIdOrderByStateNameAsc(countryId);
	}

	public State save(State state) {
		return stateRepository.save(state);
	}

	public State getByStateId(Integer stateId) {
		return stateRepository.findOne(stateId);
	}

	public State getByStateName(String stateName) {
		return stateRepository.findByStateName(stateName);
	}
	
}
