package com.sms.service.impl;

import java.sql.SQLException;
import java.text.ParseException;
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
import com.sms.domain.constant.SocietyStatus;
import com.sms.domain.constant.SubscriptionPaymentStatus;
import com.sms.domain.constant.SubscriptionStatus;
import com.sms.domain.constant.SubscriptionType;
import com.sms.domain.constant.TransactionStatus;
import com.sms.domain.constant.TransactionType;
import com.sms.exception.DuplicateDataException;
import com.sms.payload.request.SocietyRegister;
import com.sms.payload.request.SubscriptionSave;
import com.sms.payload.request.TransactionSave;
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
			
			if(SubscriptionType.parseEnum(requestPayload.getSubscriptionType()) == SubscriptionType.PAID) {
				if(TransactionType.parseEnum(requestPayload.getTransactionType()) == TransactionType.CASH) {
					if(requestPayload.getTransactionAmount() < requestPayload.getSubscriptionAmount()) {
						subscription.setSubscriptionPaymentStatus(SubscriptionPaymentStatus.PARTIAL);
					} else {
						subscription.setSubscriptionPaymentStatus(SubscriptionPaymentStatus.PAID);
					}
				} else {
					subscription.setSubscriptionPaymentStatus(SubscriptionPaymentStatus.PENDING);
				}
			} else {
				subscription.setSubscriptionPaymentStatus(SubscriptionPaymentStatus.NA);
			}
			
			subscription.setSubscriptionStatus(SubscriptionStatus.ACTIVE);
			
			subscription = subscriptionRepository.save(subscription);
			
			if(subscription.getSubscriptionType() == SubscriptionType.PAID) {
				Transaction transaction = new Transaction();
				
				transaction.setSubscriptionId(subscription.getSubscriptionId());
				transaction.setTransactionAmount(requestPayload.getTransactionAmount());
				transaction.setTransactionType(TransactionType.parseEnum(requestPayload.getTransactionType()));
				
				if(transaction.getTransactionType() == TransactionType.CASH) {
					transaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);
				} else {
					transaction.setTransactionStatus(TransactionStatus.PENDING);
				}
				
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
			society.setSocietyStatus(SocietyStatus.ACTIVE);
			
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
				societySubscription.put("subscriptionPaymentStatus", subscription.getSubscriptionPaymentStatus().toInteger());
				societySubscription.put("subscriptionPaymentStatusText", subscription.getSubscriptionPaymentStatus().toString());
				
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
	public List<Map<String, Object>> getSubscriptionBySocietyId(Integer societyId) throws ParseException {
		List<Object[]> subscriptionTransactionObjectList = subscriptionRepository.getSubscriptionTransactionBySocietyIdOrderBySubscriptionEndDateDesc(societyId, SubscriptionStatus.ACTIVE.toInteger(), SubscriptionStatus.FUTURE.toInteger(), TransactionStatus.PENDING.toInteger(), TransactionStatus.SUCCESSFUL.toInteger());
		
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		
		for(Object[] subscriptionTransactionObject : subscriptionTransactionObjectList) {
			Map<String, Object> datum = new HashMap<String, Object>();
			
			Double subscriptionAmount = ((subscriptionTransactionObject[7] != null) ? new Double(subscriptionTransactionObject[7].toString()) : 0);
			
			Double paidAmount = 0d;
			
			if(subscriptionTransactionObject[8] != null) {
				paidAmount = new Double(subscriptionTransactionObject[8].toString());
			}
			
			datum.put("subscriptionId", subscriptionTransactionObject[0]);
			datum.put("societyId", subscriptionTransactionObject[1]);
			datum.put("subscriptionStartDateText", df.format(sdf.parse(subscriptionTransactionObject[2].toString())));
			datum.put("subscriptionEndDateText", df.format(sdf.parse(subscriptionTransactionObject[3].toString())));
			datum.put("subscriptionTypeText", SubscriptionType.parseEnum((Integer) subscriptionTransactionObject[4]).toString());
			datum.put("subscriptionStatus", (Integer) subscriptionTransactionObject[5]);
			datum.put("subscriptionStatusText", SubscriptionStatus.parseEnum((Integer) subscriptionTransactionObject[5]).toString());
			datum.put("subscriptionPaymentStatus", (Integer) subscriptionTransactionObject[6]);
			datum.put("subscriptionPaymentStatusText", SubscriptionPaymentStatus.parseEnum((Integer) subscriptionTransactionObject[6]).toString());
			
			if(SubscriptionType.parseEnum((Integer) subscriptionTransactionObject[4]) == SubscriptionType.PAID) {
				datum.put("subscriptionAmount", subscriptionAmount);
				datum.put("paidAmount", paidAmount);
			} else {
				datum.put("subscriptionAmount", "NA");
				datum.put("paidAmount", "NA");
			}
			
			data.add(datum);
		}
		
		return data;
	}
	
	@Override
	public List<Map<String, Object>> getTransactionBySubscriptionId(Integer subscriptionId) {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		
		List<TransactionStatus> transactionStatusList = new ArrayList<TransactionStatus>();
		
		transactionStatusList.add(TransactionStatus.PENDING);
		transactionStatusList.add(TransactionStatus.SUCCESSFUL);
		
		List<Transaction> transactionList = transactionRepository.findBySubscriptionIdAndTransactionStatusIn(subscriptionId, transactionStatusList);
		
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		
		if(transactionList != null) {
			for(Transaction transaction : transactionList) {
				Map<String, Object> datum = new HashMap<String, Object>();
				
				datum.put("transactionId", transaction.getTransactionId());
				datum.put("transactionDateText", df.format(transaction.getTransactionCreatedOn()));
				datum.put("transactionTypeText", transaction.getTransactionType().toString());
				datum.put("transactionAmount", transaction.getTransactionAmount());
				datum.put("transactionStatusText", transaction.getTransactionStatus().toString());
				datum.put("transactionDetail", transaction.getTransactionDetail());
				
				data.add(datum);
			}
		}
		
		return data;
	}
	
	@Override
	public Map<String, Object> getSubscriptionForTransaction(Integer subscriptionId) {
		Map<String, Object> subscriptionTransaction = new HashMap<String, Object>();
		
		List<Object[]> subscriptionTransactionListObject = subscriptionRepository.getSubscriptionForTransaction(subscriptionId);
		
		if(subscriptionTransactionListObject != null) {
			Object[] subscriptionTransactionObject = subscriptionTransactionListObject.get(0);
			
			Subscription subscription = (Subscription) subscriptionTransactionObject[0];
			Double paidAmount = ((subscriptionTransactionObject[1] != null) ? new Double(subscriptionTransactionObject[1].toString()) : 0);
			Double balanceAmount = subscription.getSubscriptionAmount() - paidAmount;
			
			SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			
			subscriptionTransaction.put("subscriptionStartDateText", df.format(subscription.getSubscriptionStartDate()));
			subscriptionTransaction.put("subscriptionEndDateText", df.format(subscription.getSubscriptionEndDate()));
			subscriptionTransaction.put("subscriptionType", subscription.getSubscriptionType().toInteger());
			subscriptionTransaction.put("subscriptionDuration", subscription.getSubscriptionDuration());
			subscriptionTransaction.put("subscriptionAmount", subscription.getSubscriptionAmount());
			subscriptionTransaction.put("subscriptionStatus", subscription.getSubscriptionStatus().toInteger());
			subscriptionTransaction.put("paidAmount", paidAmount);
			subscriptionTransaction.put("balanceAmount", balanceAmount);
		}
		
		return subscriptionTransaction;
	}
	
	@Override
	public Map<String, Object> getInfoForAddSubscription(Integer societyId) throws ParseException {
		List<Object[]> infoObjectList = societyRepository.getInfoForAddSubscription(societyId);
		
		Object[] infoObject = infoObjectList.get(0);
		
		Map<String, Object> info = new HashMap<String, Object>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		
		Calendar c = Calendar.getInstance();
		
		c.setTime(sdf.parse(infoObject[1].toString()));
		
		c.add(Calendar.DATE, 1);
		
		Date subscriptionStartDate = c.getTime();
		
		info.put("societyRoomCount", new Integer(infoObject[0].toString()));
		info.put("subscriptionStartDateText", df.format(subscriptionStartDate));
		
		return info;
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

	@Override
	public Transaction saveTransaction(TransactionSave requestPayload) {
		Transaction transaction = new Transaction();
		
		transaction.setSubscriptionId(requestPayload.getSubscriptionId());
		transaction.setTransactionType(TransactionType.parseEnum(requestPayload.getTransactionType()));
		transaction.setTransactionAmount(requestPayload.getTransactionAmount());
		transaction.setTransactionDetail(requestPayload.getTransactionDetail());
		
		if(transaction.getTransactionType() == TransactionType.CASH) {
			transaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);
		} else {
			transaction.setTransactionStatus(TransactionStatus.PENDING);
		}
		
		transaction.setTransactionCreatedOn(new Date());
		
		transaction.setTransactionCreatedBy(((User) request.getAttribute("user")).getUserId());
		
		transaction = transactionRepository.save(transaction);
		
		if(transaction != null) {
			List<Object[]> subscriptionAmountInfo = subscriptionRepository.getSubscriptionAmountInfo(transaction.getSubscriptionId());
			
			Double subscriptionAmount = new Double(subscriptionAmountInfo.get(0)[0].toString());
			Double paidAmount = new Double(subscriptionAmountInfo.get(0)[1].toString());
			
			Subscription subscription = subscriptionRepository.findOne(transaction.getSubscriptionId());
			
			if(paidAmount < subscriptionAmount) {
				subscription.setSubscriptionPaymentStatus(SubscriptionPaymentStatus.PARTIAL);
			} else if(paidAmount >= subscriptionAmount) {
				subscription.setSubscriptionPaymentStatus(SubscriptionPaymentStatus.PAID);
			}
			
			subscriptionRepository.save(subscription);
			
			return transaction;
		}
		
		return null;
	}
	
	public void deleteTransaction(Integer transactionId) {
		Transaction transaction = transactionRepository.findOne(transactionId);
		
		transaction.setTransactionStatus(TransactionStatus.DELETED);
		
		transaction = transactionRepository.save(transaction);
		
		List<Object[]> subscriptionAmountInfo = subscriptionRepository.getSubscriptionAmountInfo(transaction.getSubscriptionId());
		
		Double subscriptionAmount = new Double(subscriptionAmountInfo.get(0)[0].toString());
		Double paidAmount = ((subscriptionAmountInfo.get(0)[1] != null) ? new Double(subscriptionAmountInfo.get(0)[1].toString()) : 0);
		
		Subscription subscription = subscriptionRepository.findOne(transaction.getSubscriptionId());
		
		if(paidAmount == 0) {
			subscription.setSubscriptionPaymentStatus(SubscriptionPaymentStatus.PENDING);
		} else if(paidAmount < subscriptionAmount) {
			subscription.setSubscriptionPaymentStatus(SubscriptionPaymentStatus.PARTIAL);
		}
		
		subscriptionRepository.save(subscription);
	}
}
