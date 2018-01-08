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

import com.sms.domain.Pincode;
import com.sms.payload.request.PincodeSave;
import com.sms.service.PincodeService;

@RestController
@RequestMapping(path="/pincode")
@CrossOrigin(origins="*")
public class PincodeController {
	
	@Autowired
	PincodeService pincodeService;
	
	@RequestMapping(path = "/save", method=RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<Map> save(@RequestBody PincodeSave requestBody) {
		
		Pincode pincode = new Pincode();		
		Boolean validated = true;
		
		Map response = new HashMap();
		Map messages = new HashMap();
		Map data = new HashMap();
		
		response.put("messages", messages);
		response.put("data", data);
		
		if(0 != requestBody.getPincodeId()) {
			pincode.setPincodeId(requestBody.getPincodeId());
		}

		pincode.setAreaId(requestBody.getAreaId());
		pincode.setPincodeName(requestBody.getPincodeName());
		
		if(pincode.getPincodeName().equals("")) {
			validated = false;
			messages.put("pincodeName", "Pincode cannot be blank!");
		}
		
		if(!validated) {
			return new ResponseEntity<Map>(response, HttpStatus.BAD_REQUEST);
		} else {
			if(null == pincodeService.getByPincodeName(pincode.getPincodeName())) {
				pincode = pincodeService.save(pincode);
				data.put("pincodeId", pincode.getPincodeId());
				return new ResponseEntity<Map>(response, HttpStatus.OK);
			} else {
				return new ResponseEntity<Map>(response, HttpStatus.CONFLICT);
			}
		}
	}
	
	@RequestMapping(path = "/getByAreaId", method=RequestMethod.GET)
	public ResponseEntity<List<Pincode>> getByAreaId(@RequestParam int areaId) {
		
		List<Pincode> pincodeList = pincodeService.getByAreaId(areaId);
		
		if(pincodeList.size() != 0) {
			return new ResponseEntity<List<Pincode>>(pincodeList, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Pincode>>(HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping(path="/getByPincodeId", method=RequestMethod.GET)
	public ResponseEntity<Pincode> getByPincodeId(@RequestParam Integer pincodeId) {
		Pincode pincode = pincodeService.getByPincodeId(pincodeId);
		
		if(pincode != null) {
			return new ResponseEntity<Pincode>(pincode, HttpStatus.OK);
		} else {
			return new ResponseEntity<Pincode>(HttpStatus.NO_CONTENT);
		}
	}
}
