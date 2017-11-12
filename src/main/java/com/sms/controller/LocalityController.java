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

import com.sms.domain.Locality;
import com.sms.service.LocalityService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path="/locality")
public class LocalityController {
	
	@Autowired
	LocalityService localityService;
	
	@RequestMapping(path = "/add", method=RequestMethod.PUT)
	public ResponseEntity<String> add(@RequestParam Long localityId, @RequestParam String localityName, @RequestParam Long pincodeId) {
		
		Locality locality = new Locality();
		locality.setLocalityId(localityId);
		locality.setLocalityName(localityName);
		locality.setPincodeId(pincodeId);
		
		localityService.add(locality);
		
		return new ResponseEntity<String>(HttpStatus.OK);
		
	}
	
	@RequestMapping(path = "/getByPincodeId", method=RequestMethod.GET)
	public ResponseEntity<List<Locality>> getByPincodeId(@RequestParam Long pincodeId) {
		
		List<Locality> localityList = localityService.getByPincodeId(pincodeId);
		
		return new ResponseEntity<List<Locality>>(localityList, HttpStatus.OK);
		
	}

}
