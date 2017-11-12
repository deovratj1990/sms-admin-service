package com.sms.service;

import java.util.List;

import com.sms.domain.Pincode;

public interface PincodeService {
	
	public Pincode add(Pincode pincode);
	
	public List<Pincode> getByCityId(Long cityId);

}
