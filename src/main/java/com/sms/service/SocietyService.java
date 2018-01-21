package com.sms.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.sms.domain.Society;
import com.sms.domain.Subscription;
import com.sms.exception.DuplicateDataException;
import com.sms.payload.request.SocietyRegister;
import com.sms.payload.request.SubscriptionSave;

public interface SocietyService {
	public Society search(Society society);
	
	public void register(SocietyRegister requestPayload) throws DuplicateDataException, SQLException;
	
	public List<Map<String, Object>> getAllSocietySubscription();
	
	public List<Subscription> getSubscriptionBySocietyId(Integer societyId);
	
	public Map<String, Object> getSubscriptionTransaction(Integer subscriptionId);
	
	public Subscription saveSubscription(SubscriptionSave requestPayload);
}
