package com.sms.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sms.domain.State;
import com.sms.request.body.StateSave;
import com.sms.service.StateService;

@RestController
@RequestMapping(path="/state")
@CrossOrigin(origins="*")
public class StateController {
	
	@Autowired
	StateService stateService;
	
	@RequestMapping(path = "/save", method=RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<State> save(@RequestBody StateSave requestBody) {
		
		State state = new State();
		
		long stateId = requestBody.getStateId();
		
		if(stateId != 0) {
			state.setStateId(stateId);
		}
		
		state.setCountryId(requestBody.getCountryId());
		state.setStateName(requestBody.getStateName());
		
		state = stateService.save(state);
		
		if(null != state) {
			return new ResponseEntity<State>(state, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<State>(HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@RequestMapping(path="/getAll", method=RequestMethod.GET)
	public ResponseEntity<List<Map<String, String>>> getAll() {
		List<Map<String, String>> stateList = stateService.getAll();
		
		if(stateList.size() != 0) {
			return new ResponseEntity<List<Map<String, String>>>(stateList, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Map<String, String>>>(HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping(path = "/getByCountryId", method=RequestMethod.GET)
	public ResponseEntity<List<State>> getByCountryId(@RequestParam Long countryId) {
		
		List<State> stateList = stateService.getByCountryId(countryId);
		
		if(stateList.size() != 0) {
			return new ResponseEntity<List<State>>(stateList, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<State>>(HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping(path="getByStateId", method=RequestMethod.GET)
	public ResponseEntity<State> getByStateId(@RequestParam Long stateId) {
		State state = stateService.getByStateId(stateId);
		
		if(state != null) {
			return new ResponseEntity<State>(state, HttpStatus.OK);
		} else {
			return new ResponseEntity<State>(HttpStatus.NO_CONTENT);
		}
	}
}
