package com.sms.service;

import java.util.List;
import java.util.Map;

import com.sms.domain.Society;

public interface SocietyService {
	public Society search(Society society);
	
	public int register(Society society, Map extraData);
	
	public List<Map> getAllSocietySubscription();
}
