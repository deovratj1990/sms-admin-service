package com.sms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sms.domain.City;
import com.sms.service.CityService;

@RestController
@RequestMapping(path="/city")
@CrossOrigin(origins="*")
public class CityController {

	@Autowired
	CityService cityService;
	
	@RequestMapping(path = "/add", method=RequestMethod.PUT)
	public ResponseEntity<String> add(@RequestParam Long cityId, @RequestParam String cityName, @RequestParam Long stateId ) {
		
		City city = new City();
		city.setCityId(cityId);
		city.setCityName(cityName);
		city.setStateId(stateId);
		
		cityService.add(city);
		
		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(path = "/getByStateId", method=RequestMethod.GET)
	public ResponseEntity<List<City>> getByStateId(@RequestParam Long stateId) {
		List<City> cityList = cityService.getByStateId(stateId);
		return new ResponseEntity<List<City>>(cityList, HttpStatus.OK);
		
	}
	
}
