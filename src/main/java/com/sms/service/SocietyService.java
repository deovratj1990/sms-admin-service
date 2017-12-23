package com.sms.service;

import com.sms.domain.Society;

public interface SocietyService {
	public Society search(Society society);
	
	public int register(Society society);
}
