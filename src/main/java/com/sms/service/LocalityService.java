package com.sms.service;

import java.util.List;

import com.sms.domain.Locality;

public interface LocalityService {
	
	public Locality add(Locality locality);
	
	public List<Locality> getByPincodeId(Long localityId);

}
