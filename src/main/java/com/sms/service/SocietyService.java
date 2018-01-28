package com.sms.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.sms.domain.Society;
import com.sms.domain.Subscription;
import com.sms.domain.Transaction;
import com.sms.exception.DuplicateDataException;
import com.sms.payload.request.SocietyRegister;
import com.sms.payload.request.SubscriptionSave;
import com.sms.payload.request.TransactionSave;

public interface SocietyService {
	public Society search(Society society);
	
	public void register(SocietyRegister requestPayload) throws DuplicateDataException, SQLException;
	
	public List<Map<String, Object>> getAllSocietySubscription();
	
	public List<Map<String, Object>> getSubscriptionBySocietyId(Integer societyId) throws ParseException;
	
	public List<Map<String, Object>> getTransactionBySubscriptionId(Integer subscriptionId);
	
	public Map<String, Object> getSubscriptionForTransaction(Integer subscriptionId);
	
	public Map<String, Object> getInfoForAddSubscription(Integer societyId) throws ParseException;
	
	public Subscription saveSubscription(SubscriptionSave requestPayload);
	
	public Transaction saveTransaction(TransactionSave requestPayload);
	
	public void deleteTransaction(Integer transactionId);
}
