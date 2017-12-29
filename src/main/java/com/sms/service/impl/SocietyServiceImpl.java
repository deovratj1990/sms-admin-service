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
				
				Calendar calendar = Calendar.getInstance();
				
				calendar.setTime(new Date());
				
				Date startDate = calendar.getTime();
				
				calendar.add(Calendar.MONTH, (Integer) extraData.get("subscriptionPeriodDuration"));
				
				Date endDate = calendar.getTime();
				
				User user = (User) extraData.get("user");
				
				subscriptionPeriod.setSocietyId(society.getSocietyId());
				subscriptionPeriod.setSubscriptionPeriodType(subscriptionPeriodType);
				subscriptionPeriod.setSubscriptionPeriodDuration(subscriptionPeriodDuration);
				subscriptionPeriod.setSubscriptionPeriodStartDate(startDate);
				subscriptionPeriod.setSubscriptionPeriodEndDate(endDate);
				subscriptionPeriod.setSubscriptionPeriodCreatedOn(startDate);
				subscriptionPeriod.setSubscriptionPeriodCreatedBy(user.getUserId());
				subscriptionPeriod.setSubscriptionPeriodStatus(SubscriptionPeriod.STATUS_ACTIVE);
				
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
				
				societySubscription.put("societyName", societySubscriptionListObject.get(index)[0]);
				societySubscription.put("localityName", societySubscriptionListObject.get(index)[1]);
				societySubscription.put("pincodeName", societySubscriptionListObject.get(index)[2]);
				societySubscription.put("subscriptionPeriodStartDate", societySubscriptionListObject.get(index)[3]);
				societySubscription.put("subscriptionPeriodEndDate", societySubscriptionListObject.get(index)[4]);
				societySubscription.put("subscriptionPeriodType", societySubscriptionListObject.get(index)[5]);
				societySubscription.put("subscriptionPeriodStatus", societySubscriptionListObject.get(index)[6]);
				
				societySubscriptionList.add(societySubscription);
			}
		}
		
		return societySubscriptionList;
	}
	
}
