package com.sms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/healthChecker")
public class HealthCheckerController {
	@RequestMapping(method = RequestMethod.GET, path = "/status")
	public ResponseEntity<String> status() {
		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
	}
}
