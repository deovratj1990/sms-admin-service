package com.sms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.domain.Locality;
import com.sms.repository.LocalityRepository;
import com.sms.service.LocalityService;

@Service
public class LocalityServiceImpl implements LocalityService {

	@Autowired
	LocalityRepository localityRepository;
	
	public Locality saveLocality(Locality locality) {
		
		return localityRepository.save(locality);
		
	}

	public List<Locality> getLocalityByPincodeId(Long localityId) {
		
		return localityRepository.findLocalityByPincodeId(localityId);
	
	}
}
