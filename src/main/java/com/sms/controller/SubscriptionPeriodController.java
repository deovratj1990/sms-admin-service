package com.sms.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sms.domain.SubscriptionPeriod;
import com.sms.service.SubscriptionPeriodService;

@Controller
@RequestMapping(path = "/subscriptionPeriod")
@CrossOrigin(origins = "*")
public class SubscriptionPeriodController {
	
	@Autowired
	private SubscriptionPeriodService subscriptionPeriodService;
	
	@RequestMapping(path="/save", method=RequestMethod.PUT)
	public ResponseEntity<Map> save(@RequestBody SubscriptionPeriod requestBody) {
		Boolean validated = true;
		
		Map response = new HashMap();
		Map message = new HashMap();
		Map data = new HashMap();
		
		response.put("message", message);
		response.put("data", data);
		
		DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
		SubscriptionPeriod subscriptionPeriod = new SubscriptionPeriod();
		
		if(0 != requestBody.getSubscriptionPeriodId()) {
			subscriptionPeriod.setSubscriptionPeriodId(requestBody.getSubscriptionPeriodId());
		}
		
		if(requestBody.getSubscriptionPeriodStartDate().equals("")) {
			validated = false;
			message.put("SubscriptionPeriodStartDate", "Cannot be blank!");
		} else {
		    Date result =  df.parse(target);  
		    subscriptionPeriod.setSubscriptionPeriodStartDate(requestBody.getSubscriptionPeriodStartDate());
		}

		if(requestBody.getSubscriptionPeriodEndDate().equals("")) {
			validated = false;
			message.put("SubscriptionPeriodEndDate", "Cannot be blank!");
		} else {
			subscriptionPeriod.setSubscriptionPeriodEndDate(requestBody.getSubscriptionPeriodEndDate());
		}

		if(requestBody.getSubscriptionPeriodType().equals("")) {
			validated = false;
			message.put("SubscriptionPeriodType", "Cannot be blank!");
		} else {
			subscriptionPeriod.setSubscriptionPeriodType(requestBody.getSubscriptionPeriodType());
		}

		if(0 == requestBody.getSubscriptionPeriodStatus()) {
			validated = false;
			message.put("SubscriptionPeriodStatus", "Cannot be blank!");
		} else {
			subscriptionPeriod.setSubscriptionPeriodStatus(requestBody.getSubscriptionPeriodStatus());
		}

		if(0 == requestBody.getSubscriptionPeriodAmount()) {
			validated = false;
			message.put("SubscriptionPeriodAmount", "Cannot be blank!");
		} else {
			subscriptionPeriod.setSubscriptionPeriodAmount(requestBody.getSubscriptionPeriodAmount());
		}
		
		if(!validated) {
			return new ResponseEntity<Map>(response, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Map>(response, HttpStatus.OK);
		}
	}
	
	@RequestMapping(path="/getBySubscriptionPeriodId", method = RequestMethod.GET)
	public ResponseEntity<SubscriptionPeriod> getBySubscriptionPeriodId(@RequestParam int subscriptionPeriodId) {
		if(subscriptionPeriodId > 0) {
			SubscriptionPeriod subscriptionPeriod = subscriptionPeriodService.getBySubscriptionPeriodId(subscriptionPeriodId);
			
			if(subscriptionPeriod != null) {
				return new ResponseEntity<SubscriptionPeriod>(subscriptionPeriod, HttpStatus.OK);
			} else {
				return new ResponseEntity<SubscriptionPeriod>(HttpStatus.NO_CONTENT);
			}			
		}
		
		return new ResponseEntity<SubscriptionPeriod>(HttpStatus.BAD_REQUEST);
	}
}
