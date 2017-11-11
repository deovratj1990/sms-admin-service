package com.sms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.domain.State;
import com.sms.repository.StateRepository;
import com.sms.service.StateService;

@Service
public class StateServiceImpl implements StateService {

	@Autowired
	private StateRepository stateRepository; 
	
	public List<State> getStateByCountryId(Long id) {
		return stateRepository.findByCountryId(id);
	}

	public State saveState(State state) {
		return stateRepository.save(state);
	}

}
