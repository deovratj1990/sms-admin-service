package com.sms.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.sms.domain.Subscription;
import com.sms.domain.constant.SubscriptionStatus;
import com.sms.domain.constant.SubscriptionType;
import com.sms.domain.constant.TransactionType;
import com.sms.exception.DuplicateDataException;
import com.sms.payload.request.SocietyRegister;
import com.sms.payload.request.SubscriptionSave;
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
		Integer subscriptionType = requestBody.getSubscriptionType();
		Integer subscriptionDuration = requestBody.getSubscriptionDuration();
		Float subscriptionAmount = requestBody.getSubscriptionAmount();
		Float transactionAmount = requestBody.getTransactionAmount();
		Integer transactionType = requestBody.getTransactionType();
		
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
		}
		
		if(!SubscriptionType.contains(subscriptionType)) {
			messages.put("subscriptionType", "Subscription type is invalid");
		}
		
		if(SubscriptionType.PAID.toInteger() == subscriptionType) {
			if(subscriptionDuration == 0) {
				messages.put("subscriptionDuration", "Subscription duration is mandatory");
			}
			
			if(subscriptionAmount == 0) {
				messages.put("subscriptionAmount", "Subscription amount is manadatory");
			}
			
			if(transactionAmount > 0) {
				if(!TransactionType.contains(transactionType)) {
					messages.put("transactionType", "Transaction type is invalid");
				}
			}
		}
		
		if(messages.size() == 0) {
			try {
				societyService.register(requestBody);
				
				return new ResponseEntity<Map>(HttpStatus.NO_CONTENT);
			} catch(DuplicateDataException ex) {
				return new ResponseEntity<Map>(HttpStatus.CONFLICT);
			} catch(SQLException sqlException) {
				return new ResponseEntity<Map>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		return new ResponseEntity<Map>(response, HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(path = "/getAllSocietySubscription", method = RequestMethod.GET)
	public ResponseEntity<Map> getAllSocietySubscription() {
		List<Map<String, Object>> societySubscriptionList = societyService.getAllSocietySubscription();
		
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
			List<Subscription> subscriptionList = societyService.getSubscriptionBySocietyId(societyId);
			
			if(subscriptionList.size() != 0) {
				Map response = new HashMap();
				
				response.put("subscriptionList", subscriptionList);
				
				return new ResponseEntity<Map>(response, HttpStatus.OK);
			}
			
			return new ResponseEntity<Map>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<Map>(HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(path="/saveSubscription", method=RequestMethod.PUT)
	public ResponseEntity<Map> saveSubscription(@RequestBody SubscriptionSave requestBody) {
		Boolean validated = true;
		
		Map message = new HashMap();
		Map data = new HashMap();
		
		Map response = new HashMap();
		
		response.put("message", message);
		response.put("data", data);

		if(SubscriptionType.contains(requestBody.getSubscriptionType())) {
			validated = false;
			message.put("SubscriptionType", "Cannot be blank!");
		}

		if(SubscriptionStatus.contains(requestBody.getSubscriptionStatus())) {
			validated = false;
			message.put("SubscriptionStatus", "Cannot be blank!");
		}
		
		if(validated) {
			Subscription subscription = societyService.saveSubscription(requestBody);
			
			data.put("subscriptionId", subscription.getSubscriptionId());
			
			return new ResponseEntity<Map>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<Map>(response, HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(path="/getSubscriptionTransaction", method = RequestMethod.GET)
	public ResponseEntity<Map> getSubscriptionTransaction(@RequestParam int subscriptionId) {
		if(subscriptionId > 0) {
			Map subscriptionTransaction = societyService.getSubscriptionTransaction(subscriptionId);
			
			if(subscriptionTransaction != null) {
				Map data = new HashMap();
				
				data.put("subscriptionTransaction", subscriptionTransaction);
				
				return new ResponseEntity<Map>(data, HttpStatus.OK);
			} else {
				return new ResponseEntity<Map>(HttpStatus.NO_CONTENT);
			}			
		}
		
		return new ResponseEntity<Map>(HttpStatus.BAD_REQUEST);
	}
}
