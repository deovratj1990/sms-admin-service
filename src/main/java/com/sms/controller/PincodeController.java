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

import com.sms.domain.Pincode;
import com.sms.service.PincodeService;

@RestController
@RequestMapping(path="/pincode")
@CrossOrigin(origins="*")
public class PincodeController {
	
	@Autowired
	PincodeService pincodeService;
	
	@RequestMapping(path = "/add", method=RequestMethod.PUT)
	public ResponseEntity<String> add(@RequestParam Long pincodeId, @RequestParam String pincodeName, @RequestParam Long cityId) {
				
		Pincode pincode = new Pincode();		
		pincode.setPincodeId(pincodeId);
		pincode.setPincodeName(pincodeName);
		pincode.setCityId(cityId);
		
		pincodeService.add(pincode);
		
		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		
	}
	
	@RequestMapping(path = "/getByCityId", method=RequestMethod.GET)
	public ResponseEntity<List<Pincode>> getByCityId(@RequestParam Long cityId) {
		
		List<Pincode> pincodeList = pincodeService.getByCityId(cityId);
		return new ResponseEntity<List<Pincode>>(pincodeList, HttpStatus.OK);
		
	}
	
	

}
