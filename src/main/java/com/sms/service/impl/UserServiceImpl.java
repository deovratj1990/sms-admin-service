package com.sms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.domain.User;
import com.sms.repository.UserRepository;
import com.sms.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	public User validateCredentials(String userEmail, String userPassword) {
		User user = userRepository.findByUserEmailAndUserPassword(userEmail, userPassword);
		
		return user;
	}
	
}
