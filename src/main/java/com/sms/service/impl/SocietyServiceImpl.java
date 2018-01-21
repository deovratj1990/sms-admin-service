package com.sms.service.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.sms.domain.Locality;
import com.sms.domain.Pincode;
import com.sms.domain.Society;
import com.sms.domain.Subscription;
import com.sms.domain.Transaction;
import com.sms.domain.User;
import com.sms.domain.constant.SubscriptionStatus;
import com.sms.domain.constant.SubscriptionType;
import com.sms.domain.constant.TransactionType;
import com.sms.exception.DuplicateDataException;
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
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, rollbackFor = SQLException.class)
	public void register(SocietyRegister requestPayload) throws DuplicateDataException, SQLException {
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
			subscription.setSubscriptionType(SubscriptionType.parseEnum(requestPayload.getSubscriptionType()));
			subscription.setSubscriptionDuration(requestPayload.getSubscriptionDuration());
			subscription.setSubscriptionAmount(requestPayload.getSubscriptionAmount());
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
				transaction.setTransactionType(TransactionType.parseEnum(requestPayload.getTransactionType()));
				transaction.setTransactionDetail(requestPayload.getTransactionDetail());
				transaction.setTransactionCreatedOn(today);
				transaction.setTransactionCreatedBy(user.getUserId());
				
				transaction = transactionRepository.save(transaction);
			}
			
			String societyDbName = "society_" + society.getSocietyId();
			
			societyRepository.createDatabase(societyDbName);
			
			societyRepository.setDatabase(societyDbName);
			
			societyRepository.createTables();
			
			societyRepository.insertRoles();
			
			societyRepository.insertWings(society.getSocietyId(), requestPayload.getWingName());
			
			List<String> wingNameList = requestPayload.getWingName();
			List<String> roomNameList = requestPayload.getRoomName();
			
			Integer roomCount = 0;
			
			for(int wingNameIndex = 0; wingNameIndex < wingNameList.size(); wingNameIndex++) {
				String wingName = wingNameList.get(wingNameIndex);
				String roomNamesStr = roomNameList.get(wingNameIndex);
				
				String[] roomNameArr = roomNamesStr.split(",");
				
				List<Map<String, String>> roomDetails = new ArrayList<Map<String, String>>();
				
				for(String roomName : roomNameArr) {
					if(roomName.indexOf("-") != -1) {
						String[] roomNameParts = roomName.split("-");
						
						int fromRoomNumber = Integer.parseInt(roomNameParts[0]);
						int toRoomNumber = Integer.parseInt(roomNameParts[1]);
						
						for(Integer index = fromRoomNumber; index <= toRoomNumber; index++) {
							Map<String, String> room = new HashMap<String, String>();
							
							room.put("number", index.toString());
							
							if(room.get("number").equals(requestPayload.getSecretaryRoom())) {
								room.put("role", "1");
							} else {
								room.put("role", "6");
							}
							
							roomDetails.add(room);
						}
					} else {
						Map<String, String> room = new HashMap<String, String>();
						
						room.put("number", roomName);
						
						if(room.get("number").equals(requestPayload.getSecretaryRoom())) {
							room.put("role", "1");
						} else {
							room.put("role", "6");
						}
						
						roomDetails.add(room);
					}
				}
				
				roomCount += roomDetails.size();
				
				societyRepository.insertRooms(society.getSocietyId(), societyRepository.getWingIdByWingName(wingName), roomDetails);
			}
			
			society.setSocietyWingCount(wingNameList.size());
			society.setSocietyRoomCount(roomCount);
			
			societyRepository.save(society);
			
			Map<String, Object> secretaryDetails = new HashMap<String, Object>();
			
			secretaryDetails.put("room", societyRepository.getRoomIdByWingNameAndRoomName(requestPayload.getSecretaryWing(), requestPayload.getSecretaryRoom()).toString());
			secretaryDetails.put("role", "1");
			secretaryDetails.put("mobile", requestPayload.getSecretaryMobile());
			
			societyRepository.insertSecretary(secretaryDetails);
		} else {
			throw new DuplicateDataException("Society already present.");
		}
	}

	@Override
	public List<Map<String, Object>> getAllSocietySubscription() {
		List<Object[]> societySubscriptionListObject = societyRepository.getAllSocietySubscription();
		
		List<Map<String, Object>> societySubscriptionList = new ArrayList<Map<String, Object>>();
		
		if(societySubscriptionListObject != null) {
			for(int index = 0; index < societySubscriptionListObject.size(); index++) {
				Map<String, Object> societySubscription = new HashMap<String, Object>();
				
				Object[] tempArray = societySubscriptionListObject.get(index);
				
				Society society = (Society) tempArray[0];
				Locality locality = (Locality) tempArray[1];
				Pincode pincode = (Pincode) tempArray[2];
				Subscription subscription = (Subscription) tempArray[3];
				
				SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				
				societySubscription.put("societyId", society.getSocietyId());
				societySubscription.put("subscriptionId", subscription.getSubscriptionId());
				societySubscription.put("societyName", society.getSocietyName());
				societySubscription.put("localityName", locality.getLocalityName());
				societySubscription.put("pincodeName", pincode.getPincodeName());
				societySubscription.put("subscriptionStartDate", subscription.getSubscriptionStartDate());
				societySubscription.put("subscriptionStartDateText", df.format(subscription.getSubscriptionStartDate()));
				societySubscription.put("subscriptionEndDate", subscription.getSubscriptionEndDate());
				societySubscription.put("subscriptionEndDateText", df.format(subscription.getSubscriptionEndDate()));
				societySubscription.put("subscriptionType", subscription.getSubscriptionType().toInteger());
				societySubscription.put("subscriptionTypeText", subscription.getSubscriptionType().toString());
				societySubscription.put("subscriptionStatus", subscription.getSubscriptionStatus().toInteger());
				societySubscription.put("subscriptionStatusText", subscription.getSubscriptionStatus().toString());
				
				Date subscriptionRenewalDate;
				
				if(subscription.getSubscriptionEndDate().after(new Date())) {
					subscriptionRenewalDate = new Date(subscription.getSubscriptionEndDate().getTime() + 24 * 60 * 60 * 1000);
				} else {
					subscriptionRenewalDate = new Date();
				}
				
				societySubscription.put("subscriptionRenewalDateText", df.format(subscriptionRenewalDate));
				
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
	public Map<String, Object> getSubscriptionTransaction(Integer subscriptionId) {
		List<Object[]> subscriptionTransactionListObject = subscriptionRepository.getSubscriptionTransaction(subscriptionId);
		
		if(subscriptionTransactionListObject != null) {
			Object[] subscriptionTransactionObject = subscriptionTransactionListObject.get(0);
			
			System.out.println((Float) subscriptionTransactionObject[1]);
			
			Subscription subscription = (Subscription) subscriptionTransactionObject[0];
			Float paidAmount = ((subscriptionTransactionObject[1] != null) ? (Float) subscriptionTransactionObject[1] : 0);
			Float balanceAmount = subscription.getSubscriptionAmount() - paidAmount;
			Map<String, Object> subscriptionTransaction = new HashMap<String, Object>();
			
			SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			
			subscriptionTransaction.put("subscriptionStartDateText", df.format(subscription.getSubscriptionStartDate()));
			subscriptionTransaction.put("subscriptionEndDateText", df.format(subscription.getSubscriptionEndDate()));
			subscriptionTransaction.put("subscriptionType", subscription.getSubscriptionType().toInteger());
			subscriptionTransaction.put("subscriptionDuration", subscription.getSubscriptionDuration());
			subscriptionTransaction.put("subscriptionAmount", subscription.getSubscriptionAmount());
			subscriptionTransaction.put("subscriptionStatus", subscription.getSubscriptionStatus().toInteger());
			subscriptionTransaction.put("paidAmount", paidAmount);
			subscriptionTransaction.put("balanceAmount", balanceAmount);
			
			return subscriptionTransaction;
		}
		
		return null;
	}

	@Override
	public Subscription saveSubscription(SubscriptionSave requestPayload) {
		Subscription subscription = new Subscription();
		
		if(0 != requestPayload.getSubscriptionId()) {
			subscription.setSubscriptionId(requestPayload.getSubscriptionId());
		}
		
		subscription.setSubscriptionType(SubscriptionType.parseEnum(requestPayload.getSubscriptionType()));
		
		subscription.setSubscriptionStatus(SubscriptionStatus.parseEnum(requestPayload.getSubscriptionStatus()));
		
		return null;
	}
}
