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

import com.sms.domain.City;
import com.sms.payload.request.CitySave;
import com.sms.service.CityService;

@RestController
@RequestMapping(path="/city")
@CrossOrigin(origins="*")
public class CityController {

	@Autowired
	CityService cityService;
	
	@RequestMapping(path = "/save", method=RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<Map<String, Object>> save(@RequestBody CitySave requestBody) {
		
		City city = new City();		
		Boolean validated = true;
		
		Map<String, Object> response = new HashMap<String, Object>();
		Map<String, Object> messages = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		
		response.put("messages", messages);
		response.put("data", data);
		
		if(0 != requestBody.getCityId()) {
			city.setCityId(requestBody.getCityId());
		}

		city.setStateId(requestBody.getStateId());
		city.setCityName(requestBody.getCityName());
		
		if(city.getCityName().equals("")) {
			validated = false;
			messages.put("cityName", "City cannot be blank!");
		}
		
		if(!validated) {
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		} else {
			if(null == cityService.getByCityName(city.getCityName())) {
				city = cityService.save(city);
				data.put("cityId", city.getCityId());
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			} else {
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CONFLICT);
			}
		}
	}
	
	@RequestMapping(path = "/getByStateId", method=RequestMethod.GET)
	public ResponseEntity<List<City>> getByStateId(@RequestParam Integer stateId) {
		List<City> cityList = cityService.getByStateId(stateId);
		
		if(cityList.size() != 0) {
			return new ResponseEntity<List<City>>(cityList, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<City>>(HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping(path="/getByCityId", method=RequestMethod.GET)
	public ResponseEntity<City> getByCityId(@RequestParam Integer cityId) {
		City city = cityService.getByCityId(cityId);
		
		if(city != null) {
			return new ResponseEntity<City>(city, HttpStatus.OK);
		} else {
			return new ResponseEntity<City>(HttpStatus.NO_CONTENT);
		}
	}
	
}
