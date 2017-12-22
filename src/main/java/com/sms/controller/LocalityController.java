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

import com.sms.domain.Locality;
import com.sms.domain.Pincode;
import com.sms.request.body.LocalitySave;
import com.sms.service.LocalityService;

@RestController
@RequestMapping(path="/locality")
@CrossOrigin(origins = "*")
public class LocalityController {
	
	@Autowired
	LocalityService localityService;
	
	@RequestMapping(path = "/save", method=RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<Map> save(@RequestBody LocalitySave requestBody) {
		
		Locality locality = new Locality();		
		Boolean validated = true;
		Boolean localityEdit = false;
		
		Map response = new HashMap();
		Map messages = new HashMap();
		Map data = new HashMap();
		
		response.put("messages", messages);
		response.put("data", data);
		
		if(0 != requestBody.getLocalityId()) {
			localityEdit = true;
			locality.setLocalityId(requestBody.getLocalityId());
		}

		locality.setPincodeId(requestBody.getPincodeId());
		locality.setLocalityName(requestBody.getLocalityName());
		
		if(locality.getLocalityName().equals("")) {
			validated = false;
			messages.put("localityName", "Locality cannot be blank!");
		}
		
		if(!validated) {
			return new ResponseEntity<Map>(response, HttpStatus.BAD_REQUEST);
		} else {
			if(null == localityService.getByLocalityName(locality.getLocalityName())) {
				locality = localityService.save(locality);
				data.put("localityId", locality.getLocalityId());
				return new ResponseEntity<Map>(response, HttpStatus.OK);
			} else {
				return new ResponseEntity<Map>(response, HttpStatus.CONFLICT);
			}
		}
	}
	
	@RequestMapping(path = "/getByPincodeId", method=RequestMethod.GET)
	public ResponseEntity<List<Locality>> getByPincodeId(@RequestParam int pincodeId) {
		
		List<Locality> localityList = localityService.getByPincodeId(pincodeId);
		
		if(localityList.size() != 0) {
			return new ResponseEntity<List<Locality>>(localityList, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Locality>>(HttpStatus.NO_CONTENT);
		}
		
	}

	@RequestMapping(path="/getByLocalityId", method=RequestMethod.GET)
	public ResponseEntity<Locality> getByLocalityId(@RequestParam Integer localityId) {
		Locality locality = localityService.getByLocalityId(localityId);
		
		if(locality != null) {
			return new ResponseEntity<Locality>(locality, HttpStatus.OK);
		} else {
			return new ResponseEntity<Locality>(HttpStatus.NO_CONTENT);
		}
	}
}
