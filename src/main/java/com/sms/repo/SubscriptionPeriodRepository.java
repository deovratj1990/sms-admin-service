package com.sms.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sms.domain.SubscriptionPeriod;

public interface SubscriptionPeriodRepository extends CrudRepository<SubscriptionPeriod, Integer> {
	public List<SubscriptionPeriod> findBySocietyIdOrderBySubscriptionPeriodEndDateDesc(Integer societyId);
}
