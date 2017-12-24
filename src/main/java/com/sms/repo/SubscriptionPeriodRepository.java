package com.sms.repo;

import org.springframework.data.repository.CrudRepository;

import com.sms.domain.SubscriptionPeriod;

public interface SubscriptionPeriodRepository extends CrudRepository<SubscriptionPeriod, Integer> {
	
}
