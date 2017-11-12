package com.sms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.domain.Pincode;
import com.sms.repository.PincodeRepository;
import com.sms.service.PincodeService;

@Service
public class PincodeServiceImpl implements PincodeService {

	@Autowired
	PincodeRepository pincodeRepository;
	
	public Pincode add(Pincode pincode) {
		
		return pincodeRepository.save(pincode);
	}

	public List<Pincode> getByCityId(Long cityId) {

		return pincodeRepository.findPincodeByCityId(cityId);

	}

}
