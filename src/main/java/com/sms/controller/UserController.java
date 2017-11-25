package com.sms.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sms.domain.User;
import com.sms.security.AuthUtils;
import com.sms.service.UserService;

@RestController
@RequestMapping(path = "/user")
@CrossOrigin(origins="*")
public class UserController {
	
	@Autowired
	private AuthUtils authUtils;
	
	@Autowired
	private UserService userService;
	
	@SuppressWarnings("deprecation")
	@RequestMapping(method = RequestMethod.POST, path = "/login", produces = "application/json")
	public ResponseEntity<Map> login(@RequestParam String userEmail, @RequestParam String userPassword) {
		boolean validated = true;
		
		Map data = new HashMap();
		HttpStatus status;
		
		if(userEmail.equals("")) {
			validated = false;
			data.put("userEmail", "Email Cannot Be Blank!");
		}
		
		if(userPassword.equals("")) {
			validated = false;
			data.put("userPassword", "Password Cannot Be Blank!");
		}
		
		if(validated) {
			User user = userService.validateCredentials(userEmail, userPassword);
			
			if(user != null) {
				
				data.put("form", "Login Successful!");
				data.put("user", user);
				
				Calendar cal = Calendar.getInstance();
				long nextDay = cal.getTimeInMillis() + 86400000;
		        
				String jwt = authUtils.createJWT(userEmail, nextDay);
				data.put("jwt", jwt);
				
				status = HttpStatus.OK;
			} else {
				data.put("form", "Invalid Credentials!");
				status = HttpStatus.UNAUTHORIZED;
			}
		} else {
			data.put("form", "Validation Failed!");
			status = HttpStatus.UNAUTHORIZED;
		}
		
	
		ResponseEntity<Map> responseEntity = new ResponseEntity<Map>(data, status);
		
		return responseEntity;
	}
}
