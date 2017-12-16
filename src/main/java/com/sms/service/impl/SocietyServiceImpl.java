package com.sms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.domain.Society;
import com.sms.repo.SocietyDbRepository;
import com.sms.repo.SocietyRepository;
import com.sms.service.SocietyService;

@Service
public class SocietyServiceImpl implements SocietyService {
	
	@Autowired
	private SocietyRepository societyRepository;
	
	@Autowired
	private SocietyDbRepository societyDbRepository;

	@Override
	public Society search(String societyName, Long localityId) {
		return societyRepository.findBySocietyNameAndLocalityId(societyName, localityId);
	}

	@Override
	public boolean register(Society society) {
		boolean registered = false;
		
		society = societyRepository.save(society);
		
		if(society != null) {
			String dbName = "society" + society.getSocietyId().toString();
			
			if(societyDbRepository.createDb(dbName)) {
				registered = societyDbRepository.createTables(dbName);
			}
		}
		
		return registered;
	}
	
}
