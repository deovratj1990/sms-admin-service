package com.sms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sms.domain.User;
import com.sms.service.UserService;

@RestController
@RequestMapping(path = "/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.POST, path = "/login")
	public ResponseEntity<User> login(@RequestParam("email") String email, @RequestParam("password") String password) {
		User user = userService.validateCredentials(email, password);
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
}
