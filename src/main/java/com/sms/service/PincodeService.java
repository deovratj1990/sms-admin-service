package com.sms.service;

import java.util.List;

import com.sms.domain.City;
import com.sms.domain.Pincode;

public interface PincodeService {
	
	public Pincode save(Pincode pincode);
	
	public List<Pincode> getByCityId(Integer cityId);

	public Pincode getByPincodeId(Integer pincodeId);
	
	public Pincode getByPincodeName(String pincodeName);
}
