package com.sms.controller;

import java.util.HashMap;
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
	public ResponseEntity<Map> save(@RequestBody StateSave requestBody) {
		
		State state = new State();		
		Boolean validated = true;
		Boolean stateEdit = false;
		
		Map response = new HashMap();
		Map messages = new HashMap();
		Map data = new HashMap();
		
		response.put("messages", messages);
		response.put("data", data);
		
		if(0 != requestBody.getStateId()) {
			stateEdit = true;
			state.setStateId(requestBody.getStateId());
		}

		state.setCountryId(requestBody.getCountryId());
		state.setStateName(requestBody.getStateName());
		
		if(state.getStateName().equals("")) {
			validated = false;
			messages.put("stateName", "State cannot be blank!");
		}
		
		if(!validated) {
			return new ResponseEntity<Map>(response, HttpStatus.BAD_REQUEST);
		} else {
			if(null == stateService.getByStateName(state.getStateName())) {
				state = stateService.save(state);
				data.put("stateId", state.getStateId());
				return new ResponseEntity<Map>(response, HttpStatus.OK);
			} else {
				return new ResponseEntity<Map>(response, HttpStatus.CONFLICT);
			}
		}
	}
	
	@RequestMapping(path = "/getByCountryId", method=RequestMethod.GET)
	public ResponseEntity<List<State>> getByCountryId(@RequestParam int countryId) {
		
		List<State> stateList = stateService.getByCountryId(countryId);
		
		if(stateList.size() != 0) {
			return new ResponseEntity<List<State>>(stateList, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<State>>(HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping(path="getByStateId", method=RequestMethod.GET)
	public ResponseEntity<State> getByStateId(@RequestParam int stateId) {
		State state = stateService.getByStateId(stateId);
		
		if(state != null) {
			return new ResponseEntity<State>(state, HttpStatus.OK);
		} else {
			return new ResponseEntity<State>(HttpStatus.NO_CONTENT);
		}
	}
}
