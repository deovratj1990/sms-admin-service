package com.sms.service;

import com.sms.domain.User;

public interface UserService {
	public User validateCredentials(String userEmail, String userPassword);
}
