package com.sms.service;

import java.util.List;

import com.sms.domain.Locality;

public interface LocalityService {
	
	public Locality saveLocality(Locality locality);
	
	public List<Locality> getLocalityByPincodeId(Long localityId);

}
