package com.sms.service;

import com.sms.domain.Society;

public interface SocietyService {
	public Society search(String societyName, Integer localityId);
	
	public boolean register(Society society);
}
