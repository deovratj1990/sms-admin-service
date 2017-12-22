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

import com.sms.domain.Country;
import com.sms.request.body.CountrySave;
import com.sms.service.CountryService;

@RestController
@RequestMapping(path="/country")
@CrossOrigin(origins="*")
public class CountryController {
	
	@Autowired
	CountryService countryService;
	
	@RequestMapping(path = "/save", method=RequestMethod.PUT)
	public ResponseEntity<Map> save(@RequestBody CountrySave requestBody) {
		
		Boolean validated = true;
		
		Map response = new HashMap();
		Map messages = new HashMap();
		Map data = new HashMap();
		
		response.put("messages", messages);
		response.put("data", data);
		
		Country country = new Country();
		Boolean countryEdit = false; 
		if(requestBody.getCountryId() != 0) {
			countryEdit = true;
			country.setCountryId(requestBody.getCountryId());
		}
		
		country.setCountryName(requestBody.getCountryName());
		
		if(country.getCountryName().equals("")) {
			validated = false;
			messages.put("countryName", "Country cannot be blank!");
		}
		
		if(!validated) {
			return new ResponseEntity<Map>(response, HttpStatus.BAD_REQUEST);
		} else {
			if(countryService.getByCountryName(country.getCountryName()) == null) {
				country = countryService.save(country);
				data.put("countryId", country.getCountryId());
				return new ResponseEntity<Map>(response, HttpStatus.OK);
			} else {
				return new ResponseEntity<Map>(HttpStatus.CONFLICT);
			}
		}
	}
	
	@RequestMapping(value="/getByCountryId", method=RequestMethod.GET)
	public ResponseEntity<Country> getByCountryId(@RequestParam int countryId){

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
