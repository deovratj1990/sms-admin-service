package com.sms.repository;

import org.springframework.data.repository.CrudRepository;

import com.sms.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {
	public User findByEmailAndPassword(String email, String password);
}
