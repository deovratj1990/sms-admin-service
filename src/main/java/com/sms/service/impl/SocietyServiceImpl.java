package com.sms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.domain.Society;
import com.sms.repo.SocietyRepository;
import com.sms.service.SocietyService;

@Service
public class SocietyServiceImpl implements SocietyService {
	
	@Autowired
	private SocietyRepository societyRepository;

	@Override
	public Society search(String societyName, Long localityId) {
		return societyRepository.findBySocietyNameAndLocalityId(societyName, localityId);
	}
	
}
