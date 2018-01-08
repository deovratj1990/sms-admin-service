package com.sms.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sms.domain.User;
import com.sms.payload.request.UserLogin;
import com.sms.security.auth.TokenManager;
import com.sms.service.UserService;

@RestController
@RequestMapping(path = "/user")
@CrossOrigin(origins="*")
public class UserController {
	
	@Autowired
	private TokenManager tokenManager;
	
	@Autowired
	private UserService userService;
	
	@SuppressWarnings("deprecation")
	@RequestMapping(method = RequestMethod.POST, path = "/login", produces = "application/json")
	public ResponseEntity<Map> login(@RequestBody UserLogin requestBody) {
		boolean validated = true;
		
		Map data = new HashMap();
		HttpStatus status;
		
		String userEmail = requestBody.getUserEmail();
		String userPassword = requestBody.getUserPassword();
		
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
				String token = tokenManager.generateToken(userEmail);
				
				data.put("form", "Login Successful!");
				data.put("user", user);
				data.put("accessToken", token);
				
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
