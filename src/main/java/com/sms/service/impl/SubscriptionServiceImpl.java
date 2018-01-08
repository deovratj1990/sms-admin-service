package com.sms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.domain.SubscriptionPeriod;
import com.sms.repo.SubscriptionPeriodRepository;
import com.sms.service.SubscriptionPeriodService;

@Service
public class SubscriptionPeriodServiceImpl implements SubscriptionPeriodService{

	@Autowired
	private SubscriptionPeriodRepository subscriptionPeriodRepository;
	
	@Override
	public SubscriptionPeriod getBySubscriptionPeriodId (Integer subscriptionPeriodId) {
		SubscriptionPeriod subscriptionPeriodList = subscriptionPeriodRepository.findOne(subscriptionPeriodId);
		
		return subscriptionPeriodList;
	}
}
