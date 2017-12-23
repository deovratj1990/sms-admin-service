package com.sms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.domain.Pincode;
import com.sms.repo.PincodeRepository;
import com.sms.service.PincodeService;

@Service
public class PincodeServiceImpl implements PincodeService {

	@Autowired
	PincodeRepository pincodeRepository;
	
	public Pincode save(Pincode pincode) {
		
		return pincodeRepository.save(pincode);
	}

	public List<Pincode> getByAreaId(Integer areaId) {

		return pincodeRepository.findPincodeByAreaIdOrderByPincodeNameAsc(areaId);

	}

	@Override
	public Pincode getByPincodeId(Integer pincodeId) {
		return pincodeRepository.findOne(pincodeId);
	}

	@Override
	public Pincode getByPincodeName(String pincodeName) {
		return pincodeRepository.findByPincodeName(pincodeName);
	}

}
