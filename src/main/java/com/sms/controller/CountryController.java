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
@CrossOrigin(origins="*")
@RequestMapping(path="/country")
public class CountryController {
	
	@Autowired
	CountryService countryService;
	
	@RequestMapping(path = "/add", method=RequestMethod.PUT)
	public ResponseEntity<Country> add(@RequestParam String countryName) {
		
		Country country = new Country();
		country.setCountryName(countryName);
		Country savedCountry = countryService.add(country);
		
		if(null == savedCountry) {
			return new ResponseEntity<Country>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<Country>(savedCountry, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.PUT)
	public ResponseEntity<Country> edit(@RequestParam Long countryId , @RequestParam String countryName){
		Country country = new Country();
		country.setCountryId(countryId);
		country.setCountryName(countryName);
		
		Country editCountry = countryService.edit(country);
		
		if(null == editCountry) {
			return new ResponseEntity<Country>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<Country>(country, HttpStatus.OK);
		}
		
	}
	
	@RequestMapping(value="/getByCountryId", method=RequestMethod.GET)
	public ResponseEntity<Country> getByCountryId(@RequestParam Long countryId){

		Country country = countryService.getByCountryId(countryId);
		
		return new ResponseEntity<Country>(country, HttpStatus.OK);
	}
		
	@RequestMapping(path="/getAll", method=RequestMethod.GET)
	public ResponseEntity<List<Country>> getAll() {
		
		List<Country> countries = countryService.getAll();
		
		return new ResponseEntity<List<Country>>(countries, HttpStatus.OK);
	}

}
