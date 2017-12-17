package com.sms.repo;

import org.springframework.data.repository.CrudRepository;

import com.sms.domain.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	public User findByUserEmailAndUserPassword(String userEmail, String userPassword);
}
