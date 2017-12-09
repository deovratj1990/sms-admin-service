package com.sms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.domain.State;
import com.sms.repo.StateRepository;
import com.sms.service.StateService;

@Service
public class StateServiceImpl implements StateService {

	@Autowired
	private StateRepository stateRepository; 
	
	public List<State> getByCountryId(Long countryId) {
		return stateRepository.findByCountryIdOrderByStateNameAsc(countryId);
	}

	public State save(State state) {
		return stateRepository.save(state);
	}

	public List<Map<String, String>> getAll() {
		List<Map<String, String>> states = new ArrayList<Map<String, String>>();
		
		for(Object[] rowObject : stateRepository.getAll()) {
			Map<String, String> row = new HashMap<String, String>();
			
			row.put("countryName", rowObject[0].toString());
			row.put("stateId", rowObject[1].toString());
			row.put("stateName", rowObject[2].toString());
			
			states.add(row);
		}
		
		return states;
	}

	public State getByStateId(Long stateId) {
		return stateRepository.findOne(stateId);
	}
	
}
