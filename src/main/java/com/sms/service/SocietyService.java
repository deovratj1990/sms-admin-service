package com.sms.service;

import java.util.List;
import java.util.Map;

import com.sms.domain.Society;
import com.sms.domain.SubscriptionPeriod;

public interface SocietyService {
	public Society search(Society society);
	
	public int register(Society society, Map extraData);
	
	public List<Map> getAllSocietySubscription();
	
	public List<SubscriptionPeriod> getSubscriptionBySocietyId(Integer societyId);
}
