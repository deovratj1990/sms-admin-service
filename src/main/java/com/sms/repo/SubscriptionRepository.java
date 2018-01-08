package com.sms.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sms.domain.Subscription;

public interface SubscriptionRepository extends CrudRepository<Subscription, Integer> {
	public List<Subscription> findBySocietyIdOrderBySubscriptionEndDateDesc(Integer societyId);
}
