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
import com.sms.domain.Country;
import com.sms.domain.Locality;
import com.sms.domain.Pincode;
import com.sms.domain.State;
import com.sms.service.CityService;
import com.sms.service.CountryService;
import com.sms.service.LocalityService;
import com.sms.service.PincodeService;
import com.sms.service.StateService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path="/address")
public class AddressController {
	
	@Autowired
	CountryService countryService;
	
	@Autowired
	StateService stateService;
	
	@Autowired
	CityService cityService;
	
	@Autowired
	PincodeService pincodeService;
	
	@Autowired
	LocalityService localityService;
	
	@RequestMapping(path = "/addCountry", method=RequestMethod.PUT)
	public ResponseEntity<Country> addCountry(/*@RequestParam Long countryId, */@RequestParam String countryName) {
		
		Country country = new Country();
		country.setCountryName(countryName);
		Country savedCountry = countryService.addCountry(country);
		
		if(null == savedCountry) {
			return new ResponseEntity<Country>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<Country>(savedCountry, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/editCountry", method=RequestMethod.PUT)
	public ResponseEntity<Country> editCountry(@RequestParam Long countryId , @RequestParam String countryName){
		Country country = new Country();
		country.setCountryId(countryId);
		country.setCountryName(countryName);
		
		Country editCountry = countryService.editCountry(country);
		
		if(null == editCountry) {
			return new ResponseEntity<Country>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<Country>(country, HttpStatus.OK);
		}
		
	}
	
	@RequestMapping(value="/getCountry", method=RequestMethod.GET)
	public ResponseEntity<Country> getCountry(@RequestParam Long countryId){

		Country country = countryService.getCountryByCountryId(countryId);
		
		return new ResponseEntity<Country> (country, HttpStatus.OK);
	}
	
	@RequestMapping(value="/getCountries", method=RequestMethod.GET)
	public ResponseEntity<List<Country>> getCountries(){

		List<Country> countries = countryService.getCountries();
		
		return new ResponseEntity<List<Country>> (countries, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/addState", method=RequestMethod.PUT)
	public ResponseEntity<String> addState(@RequestParam Long countryId, @RequestParam Long stateId, @RequestParam String stateName) {
		
		State state = new State();
		state.setCountryId(countryId);
		state.setStateId(stateId);
		state.setStateName(stateName); 
		
		stateService.saveState(state);
		
		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		
	}
	
	@RequestMapping(path = "/getStates", method=RequestMethod.GET)
	public ResponseEntity<List<State>> getState(@RequestParam Long countryId) {
		
		List<State> states = stateService.getStateByCountryId(countryId);
		
		ResponseEntity<List<State>> response = new ResponseEntity<List<State>>(states, HttpStatus.OK);
		
		return response;
	}
	
	@RequestMapping(path = "/addCity", method=RequestMethod.PUT)
	public ResponseEntity<String> addCity(@RequestParam Long cityId, @RequestParam String cityName, @RequestParam Long stateId ) {
		
		City city = new City();
		city.setCityId(cityId);
		city.setCityName(cityName);
		city.setStateId(stateId);
		
		cityService.saveCity(city);
		
		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(path = "/getCities", method=RequestMethod.GET)
	public ResponseEntity<List<City>> getCities(@RequestParam Long stateId) {
		List<City> cityList = cityService.getCityByStateId(stateId);
		return new ResponseEntity<List<City>>(cityList, HttpStatus.OK);
		
	}
	
	@RequestMapping(path = "/addPincode", method=RequestMethod.PUT)
	public ResponseEntity<String> addPincode(@RequestParam Long pincodeId, @RequestParam String pincodeName, @RequestParam Long cityId) {
				
		Pincode pincode = new Pincode();		
		pincode.setPincodeId(pincodeId);
		pincode.setPincodeName(pincodeName);
		pincode.setCityId(cityId);
		
		pincodeService.savePincode(pincode);
		
		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		
	}
	
	@RequestMapping(path = "/getPincode", method=RequestMethod.GET)
	public ResponseEntity<List<Pincode>> getPincode(@RequestParam Long cityId) {
		
		List<Pincode> pincodeList = pincodeService.getPincodeByCityId(cityId);
		return new ResponseEntity<List<Pincode>>(pincodeList, HttpStatus.OK);
		
	}
	
	@RequestMapping(path = "/addLocality", method=RequestMethod.PUT)
	public ResponseEntity<String> addLocality(@RequestParam Long localityId, @RequestParam String localityName, @RequestParam Long pincodeId) {
		
		Locality locality = new Locality();
		locality.setLocalityId(localityId);
		locality.setLocalityName(localityName);
		locality.setPincodeId(pincodeId);
		
		localityService.saveLocality(locality);
		
		return new ResponseEntity<String>(HttpStatus.OK);
		
	}
	
	@RequestMapping(path = "/getLocality", method=RequestMethod.GET)
	public ResponseEntity<List<Locality>> getLocality(@RequestParam Long pincodeId) {
		
		List<Locality> localityList = localityService.getLocalityByPincodeId(pincodeId);
		
		return new ResponseEntity<List<Locality>> (localityList, HttpStatus.OK);
		
	}


}
