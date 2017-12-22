package com.sms.service;

import java.util.List;

import com.sms.domain.Locality;

public interface LocalityService {
	
	public Locality save(Locality locality);
	
	public List<Locality> getByPincodeId(Integer localityId);

	public Locality getByLocalityId(Integer localityId);
	
	public Locality getByLocalityName(String localityName);
}
