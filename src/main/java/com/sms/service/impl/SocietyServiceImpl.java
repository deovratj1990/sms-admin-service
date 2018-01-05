package com.sms.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.domain.Society;
import com.sms.domain.SubscriptionPeriod;
import com.sms.domain.User;
import com.sms.repo.SocietyRepository;
import com.sms.repo.SubscriptionPeriodRepository;
import com.sms.service.SocietyService;

@Service
public class SocietyServiceImpl implements SocietyService {
	
	@Autowired
	private SocietyRepository societyRepository;
	
	@Autowired
	private SubscriptionPeriodRepository subscriptionPeriodRepository;

	@Override
	public Society search(Society society) {
		return societyRepository.findBySocietyNameAndLocalityId(society.getSocietyName(), society.getLocalityId());
	}

	@Override
	@Transactional
	public int register(Society society, Map extraData) {
		try {
			if(search(society) == null) {
				society = societyRepository.save(society);
				
				SubscriptionPeriod subscriptionPeriod = new SubscriptionPeriod();
				
				int subscriptionPeriodType = (Integer) extraData.get("subscriptionPeriodType");
				
				int subscriptionPeriodDuration = (Integer) extraData.get("subscriptionPeriodDuration");
				
				Date today = new Date();
				
				Calendar calendar = Calendar.getInstance();
				
				calendar.setTime(today);
				
				Date startDate = calendar.getTime();
				
				calendar.add(Calendar.MONTH, (Integer) extraData.get("subscriptionPeriodDuration"));
				
				Date endDate = calendar.getTime();
				
				User user = (User) extraData.get("user");
				
				subscriptionPeriod.setSocietyId(society.getSocietyId());
				subscriptionPeriod.setSubscriptionPeriodType(subscriptionPeriodType);
				subscriptionPeriod.setSubscriptionPeriodDuration(subscriptionPeriodDuration);
				subscriptionPeriod.setSubscriptionPeriodStartDate(startDate);
				subscriptionPeriod.setSubscriptionPeriodEndDate(endDate);
				subscriptionPeriod.setSubscriptionPeriodCreatedOn(today);
				subscriptionPeriod.setSubscriptionPeriodCreatedBy(user.getUserId());
				subscriptionPeriod.setSubscriptionPeriodStatus(SubscriptionPeriod.STATUS_ACTIVE);
				subscriptionPeriod.setSubscriptionPeriodStatusModifiedBy(user.getUserId());
				
				subscriptionPeriodRepository.save(subscriptionPeriod);
				
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
				int tempArrayIndex = 0;
				
				societySubscription.put("societyId", tempArray[tempArrayIndex++]);
				societySubscription.put("subscriptionPeriodId", tempArray[tempArrayIndex++]);
				societySubscription.put("societyName", tempArray[tempArrayIndex++]);
				societySubscription.put("localityName", tempArray[tempArrayIndex++]);
				societySubscription.put("pincodeName", tempArray[tempArrayIndex++]);
				societySubscription.put("subscriptionPeriodStartDate", tempArray[tempArrayIndex++]);
				societySubscription.put("subscriptionPeriodEndDate", tempArray[tempArrayIndex++]);
				societySubscription.put("subscriptionPeriodType", tempArray[tempArrayIndex++]);
				societySubscription.put("subscriptionPeriodStatus", tempArray[tempArrayIndex++]);
				
				societySubscriptionList.add(societySubscription);
			}
		}
		
		return societySubscriptionList;
	}

	@Override
	public List<SubscriptionPeriod> getSubscriptionBySocietyId(Integer societyId) {
		List<SubscriptionPeriod> subscriptionPeriodList = subscriptionPeriodRepository.findBySocietyIdOrderBySubscriptionPeriodEndDateDesc(societyId);
		
		return subscriptionPeriodList;
	}
}
