package com.sms.service;

import java.util.List;

import com.sms.domain.Pincode;

public interface PincodeService {
	
	public Pincode savePincode(Pincode pincode);
	
	public List<Pincode> getPincodeByCityId(Long cityId);

}
