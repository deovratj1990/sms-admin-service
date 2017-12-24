package com.sms.service.impl;

import java.util.Calendar;
import java.util.Date;
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
				
				subscriptionPeriod.setScocietyId(society.getSocietyId());
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
	
}
