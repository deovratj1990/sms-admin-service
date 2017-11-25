package com.sms.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sms.domain.State;
import com.sms.service.StateService;

@RestController
@RequestMapping(path="/state")
@CrossOrigin(origins="*")
public class StateController {
	
	@Autowired
	StateService stateService;
	
	@RequestMapping(path = "/add", method=RequestMethod.PUT)
	public ResponseEntity<State> add(@RequestParam Long countryId, @RequestParam String stateName) {
		
		State state = new State();
		state.setCountryId(countryId);
		state.setStateName(stateName); 
		
		State addState = stateService.add(state);
		
		if(null == addState) {
			return new ResponseEntity<State>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<State>(addState, HttpStatus.OK);
		}		
	}
	
	@RequestMapping(path="/getAll", method=RequestMethod.GET)
	public ResponseEntity<List<Map<String, String>>> getAll() {
		List<Map<String, String>> states = stateService.getAll();
		
		return new ResponseEntity<List<Map<String, String>>>(states, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/getByCountryId", method=RequestMethod.GET)
	public ResponseEntity<List<State>> getByCountryId(@RequestParam Long countryId) {
		
		List<State> states = stateService.getByCountryId(countryId);
		
		ResponseEntity<List<State>> response = new ResponseEntity<List<State>>(states, HttpStatus.OK);
		
		return response;
	}
	
	@RequestMapping(path="getByStateId", method=RequestMethod.GET)
	public ResponseEntity<State> getByStateId(@RequestParam Long stateId) {
		State state = stateService.getByStateId(stateId);
		
		return new ResponseEntity<State>(state, HttpStatus.OK);
	}
}
