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

import com.sms.domain.Country;
import com.sms.service.CountryService;

@RestController
@RequestMapping(path="/country")
@CrossOrigin(origins="*")
public class CountryController {
	
	@Autowired
	CountryService countryService;
	
	@RequestMapping(path = "/add", method=RequestMethod.PUT)
	public ResponseEntity<String> add(@RequestParam String countryName) {
		
		Country country = new Country();
		
		country.setCountryName(countryName);
		
		if(null != countryService.add(country)) {
			return new ResponseEntity<String>(HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.PUT)
	public ResponseEntity<String> edit(@RequestParam Long countryId , @RequestParam String countryName){
		Country country = new Country();
		
		country.setCountryId(countryId);
		country.setCountryName(countryName);
		
		if(null != countryService.edit(country)) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@RequestMapping(value="/getByCountryId", method=RequestMethod.GET)
	public ResponseEntity<Country> getByCountryId(@RequestParam Long countryId){

		Country country = countryService.getByCountryId(countryId);
		
		if(country != null) {
			return new ResponseEntity<Country>(country, HttpStatus.OK);
		} else {
			return new ResponseEntity<Country>(HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping(path="/getAll", method=RequestMethod.GET)
	public ResponseEntity<List<Country>> getAll() {
		
		List<Country> countryList = countryService.getAll();
		
		if(countryList.size() != 0) {
			return new ResponseEntity<List<Country>>(countryList, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Country>>(HttpStatus.NO_CONTENT);
		}
	}

}
