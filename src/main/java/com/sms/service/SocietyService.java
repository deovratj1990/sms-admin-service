package com.sms.service;

import java.util.List;
import java.util.Map;

import com.sms.domain.Society;
import com.sms.domain.Subscription;
import com.sms.payload.request.SocietyRegister;
import com.sms.payload.request.SubscriptionSave;

public interface SocietyService {
	public Society search(Society society);
	
	public int register(SocietyRegister requestPayload);
	
	public List<Map> getAllSocietySubscription();
	
	public List<Subscription> getSubscriptionBySocietyId(Integer societyId);
	
	public Subscription getSubscription(Integer subscriptionId);
	
	public Subscription saveSubscription(SubscriptionSave requestPayload);
}
