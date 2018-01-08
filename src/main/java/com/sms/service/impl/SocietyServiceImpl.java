package com.sms.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.domain.Society;
import com.sms.domain.Subscription;
import com.sms.domain.SubscriptionStatus;
import com.sms.domain.SubscriptionType;
import com.sms.domain.Transaction;
import com.sms.domain.TransactionType;
import com.sms.domain.User;
import com.sms.payload.request.SocietyRegister;
import com.sms.payload.request.SubscriptionSave;
import com.sms.repo.SocietyRepository;
import com.sms.repo.SubscriptionRepository;
import com.sms.repo.TransactionRepository;
import com.sms.service.SocietyService;

@Service
public class SocietyServiceImpl implements SocietyService {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private SocietyRepository societyRepository;
	
	@Autowired
	private SubscriptionRepository subscriptionRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public Society search(Society society) {
		return societyRepository.findBySocietyNameAndLocalityId(society.getSocietyName(), society.getLocalityId());
	}

	@Override
	@Transactional
	public int register(SocietyRegister requestPayload) {
		try {
			User user = (User) request.getAttribute("user");
			
			Society society = new Society();
			
			society.setSocietyName(requestPayload.getSocietyName());
			society.setLocalityId(requestPayload.getLocalityId());
			
			if(search(society) == null) {
				society = societyRepository.save(society);
				
				Date today = new Date();
				
				Calendar calendar = Calendar.getInstance();
				
				calendar.setTime(today);
				
				Date startDate = calendar.getTime();
				
				calendar.add(Calendar.MONTH, requestPayload.getSubscriptionDuration());
				
				Date endDate = calendar.getTime();
				
				Subscription subscription = new Subscription();
				
				subscription.setSocietyId(society.getSocietyId());
				subscription.setSubscriptionType(SubscriptionType.getFor(requestPayload.getSubscriptionType()));
				subscription.setSubscriptionDuration(requestPayload.getSubscriptionDuration());
				subscription.setSubscriptionCreatedBy(user.getUserId());
				subscription.setSubscriptionStartDate(startDate);
				subscription.setSubscriptionEndDate(endDate);
				subscription.setSubscriptionCreatedOn(today);
				subscription.setSubscriptionStatus(SubscriptionStatus.ACTIVE);
				subscription.setSubscriptionStatusModifiedBy(null);
				
				subscription = subscriptionRepository.save(subscription);
				
				if(subscription.getSubscriptionType() == SubscriptionType.PAID) {
					Transaction transaction = new Transaction();
					
					transaction.setSubscriptionId(subscription.getSubscriptionId());
					transaction.setTransactionAmount(requestPayload.getTransactionAmount());
					transaction.setTransactionType(TransactionType.getFor(requestPayload.getTransactionType()));
					transaction.setTransactionDetail(requestPayload.getTransactionDetail());
					transaction.setTransactionCreatedOn(today);
					transaction.setTransactionCreatedBy(user.getUserId());
					
					transaction = transactionRepository.save(transaction);
				}
				
				String societyDbName = "society_" + society.getSocietyId();
				
				societyRepository.createDb(societyDbName);
				
				societyRepository.createDbTables(societyDbName);
				
				return 1;
			} else {
				return -1;
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			
			return 0;
		}
	}

	@Override
	public List<Map> getAllSocietySubscription() {
		List<Object[]> societySubscriptionListObject = societyRepository.getAllSocietySubscription();
		List<Map> societySubscriptionList = new ArrayList<Map>();
		
		if(societySubscriptionListObject != null) {
			for(int index = 0; index < societySubscriptionListObject.size(); index++) {
				Map societySubscription = new HashMap();
				
				Object[] tempArray = societySubscriptionListObject.get(index);
				int tempArrayIndex = -1;
				
				SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				
				societySubscription.put("societyId", tempArray[++tempArrayIndex]);
				societySubscription.put("subscriptionId", tempArray[++tempArrayIndex]);
				societySubscription.put("societyName", tempArray[++tempArrayIndex]);
				societySubscription.put("localityName", tempArray[++tempArrayIndex]);
				societySubscription.put("pincodeName", tempArray[++tempArrayIndex]);
				societySubscription.put("subscriptionStartDate", tempArray[++tempArrayIndex]);
				societySubscription.put("subscriptionStartDateText", df.format(tempArray[tempArrayIndex]));
				societySubscription.put("subscriptionEndDate", tempArray[++tempArrayIndex]);
				societySubscription.put("subscriptionEndDateText", df.format(tempArray[tempArrayIndex]));
				societySubscription.put("subscriptionType", tempArray[++tempArrayIndex]);
				societySubscription.put("subscriptionTypeText", SubscriptionType.getFor((Integer) tempArray[tempArrayIndex]).getText());
				societySubscription.put("subscriptionStatus", tempArray[++tempArrayIndex]);
				societySubscription.put("subscriptionStatusText", SubscriptionStatus.getFor((Integer) tempArray[tempArrayIndex]).getText());
				
				societySubscriptionList.add(societySubscription);
			}
		}
		
		return societySubscriptionList;
	}

	@Override
	public List<Subscription> getSubscriptionBySocietyId(Integer societyId) {
		List<Subscription> subscriptionList = subscriptionRepository.findBySocietyIdOrderBySubscriptionEndDateDesc(societyId);
		
		return subscriptionList;
	}
	
	@Override
	public Subscription getSubscription(Integer subscriptionId) {
		Subscription subscription = subscriptionRepository.findOne(subscriptionId);
		
		return subscription;
	}

	@Override
	public Subscription saveSubscription(SubscriptionSave requestPayload) {
		Subscription subscription = new Subscription();
		
		if(0 != requestPayload.getSubscriptionId()) {
			subscription.setSubscriptionId(requestPayload.getSubscriptionId());
		}
		
		subscription.setSubscriptionType(SubscriptionType.getFor(requestPayload.getSubscriptionType()));
		
		subscription.setSubscriptionStatus(SubscriptionStatus.getFor(requestPayload.getSubscriptionStatus()));
		
		
		
		return null;
	}
}
