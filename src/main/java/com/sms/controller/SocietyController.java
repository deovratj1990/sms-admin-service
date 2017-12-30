package com.sms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sms.domain.Society;
import com.sms.domain.SubscriptionPeriod;
import com.sms.request.body.SocietyRegister;
import com.sms.service.SocietyService;

@Controller
@RequestMapping(path = "/society")
@CrossOrigin(origins = "*")
public class SocietyController {
	@Autowired
	private SocietyService societyService;
	
	@RequestMapping(path = "/register", method = RequestMethod.PUT, consumes = "application/json", produces="application/json")
	public ResponseEntity<Map> register(HttpServletRequest request, @RequestBody SocietyRegister requestBody) {
		String societyName = requestBody.getSocietyName();
		int countryId = requestBody.getCountryId();
		int stateId = requestBody.getStateId();
		int cityId = requestBody.getCityId();
		int areaId = requestBody.getAreaId();
		int pincodeId = requestBody.getPincodeId();
		int localityId = requestBody.getLocalityId();
		List<String> wingNameList = requestBody.getWingName();
		List<String> roomNameList = requestBody.getRoomName();
		String secretaryWing = requestBody.getSecretaryWing();
		String secretaryRoom = requestBody.getSecretaryRoom();
		String secretaryMobile = requestBody.getSecretaryMobile();
		int subscriptionPeriodType = requestBody.getSubscriptionPeriodType();
		int subscriptionPeriodDuration = requestBody.getSubscriptionPeriodDuration();
		
		Map messages = new HashMap();
		Map data = new HashMap();
		
		Map response = new HashMap();
		
		response.put("messages", messages);
		response.put("data", data);
		
		if(societyName == null || societyName.trim().length() == 0) {
			messages.put("societyName", "Society Name is manadatory");
		}
		
		if(countryId == 0) {
			messages.put("countryId", "Country is manadatory");
		}
		
		if(stateId == 0) {
			messages.put("stateId", "State is manadatory");
		}
		
		if(cityId == 0) {
			messages.put("cityId", "City is manadatory");
		}
		
		if(areaId == 0) {
			messages.put("areaId", "Area is manadatory");
		}
		
		if(pincodeId == 0) {
			messages.put("pincodeId", "Pincode is manadatory");
		}
		
		if(localityId == 0) {
			messages.put("localityId", "Locality is manadatory");
		}
		
		if(wingNameList == null || wingNameList.size() == 0) {
			messages.put("societyWingCount", "Wings is manadatory");
		} else {
			Map<String, String> wingNameMessages = new HashMap<String, String>();
			Map<String, String> roomNameMessages = new HashMap<String, String>();
			
			for(int index = 0; index < wingNameList.size(); index++) {
				String wingName = wingNameList.get(index);
				
				if(wingName == null || wingName.trim().length() == 0) {
					wingNameMessages.put("wingName_" + index, "Wing name is manadatory");
				}
				
				String roomName = roomNameList.get(index);
				
				if(roomName == null || roomName.trim().length() == 0) {
					roomNameMessages.put("roomName_" + index, "Room name is manadatory");
				}
			}
			
			if(wingNameMessages.size() != 0) {
				messages.put("wingName", wingNameMessages);
			}
			
			if(roomNameMessages.size() != 0) {
				messages.put("roomName", roomNameMessages);
			}
		}
		
		if(secretaryWing == null || secretaryWing.trim().length() == 0) {
			messages.put("secretaryWing", "Secretary wing is manadatory");
		}
		
		if(secretaryRoom == null || secretaryRoom.trim().length() == 0) {
			messages.put("secretaryRoom", "Secretary room is manadatory");
		}
		
		if(secretaryMobile == null || secretaryMobile.trim().length() == 0) {
			messages.put("secretaryMobile", "Secretary mobile is manadatory");
		} else if(!Pattern.matches("[0-9]{10,10}", secretaryMobile.trim())) {
			messages.put("secretaryMobile", "Secretary mobile is not valid");
		}
		
		if(subscriptionPeriodType == 0) {
			messages.put("subscriptionPeriodType", "Subscription period type is manadatory");
		}
		
		if(subscriptionPeriodDuration == 0) {
			messages.put("subscriptionPeriodDuration", "Subscription period duration is manadatory");
		}
		
		if(messages.size() == 0) {
			Society society = new Society();
			
			society.setSocietyName(societyName);
			society.setLocalityId(localityId);
			
			Map extraData = new HashMap();
			
			extraData.put("subscriptionPeriodType", subscriptionPeriodType);
			extraData.put("subscriptionPeriodDuration", subscriptionPeriodDuration);
			extraData.put("user", request.getAttribute("user"));
			
			int created = societyService.register(society, extraData);
			
			if(created == 1) {
				return new ResponseEntity<Map>(HttpStatus.NO_CONTENT);
			} else if(created == -1) {
				return new ResponseEntity<Map>(HttpStatus.CONFLICT);
			}
			
			return new ResponseEntity<Map>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Map>(response, HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(path = "/getAllSocietySubscription", method = RequestMethod.GET)
	public ResponseEntity<Map> getAllSocietySubscription() {
		List<Map> societySubscriptionList = societyService.getAllSocietySubscription();
		
		if(societySubscriptionList.size() != 0) {
			Map response = new HashMap();
			
			response.put("societySubscriptionList", societySubscriptionList);
			
			return new ResponseEntity<Map>(response, HttpStatus.OK);
		}
		
		return new ResponseEntity<Map>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(path = "/getSubscriptionBySocietyId", method = RequestMethod.GET)
	public ResponseEntity<Map> getSubscriptionBySocietyId(@RequestParam int societyId) {
		if(societyId > 0) {
			List<SubscriptionPeriod> subscriptionList = societyService.getSubscriptionBySocietyId(societyId);
			
			if(subscriptionList.size() != 0) {
				Map response = new HashMap();
				
				response.put("subscriptionList", subscriptionList);
				
				return new ResponseEntity<Map>(response, HttpStatus.OK);
			}
			
			return new ResponseEntity<Map>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<Map>(HttpStatus.BAD_REQUEST);
	}
}
