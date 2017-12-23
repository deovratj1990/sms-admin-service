package com.sms.service.impl;

import javax.transaction.Transactional;

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
	public Society search(Society society) {
		return societyRepository.findBySocietyNameAndLocalityId(society.getSocietyName(), society.getLocalityId());
	}

	@Override
	@Transactional
	public int register(Society society) {
		try {
			if(search(society) == null) {
				society = societyRepository.save(society);
				
				String societyDbName = "society" + society.getSocietyId();
				
				societyRepository.createDb(societyDbName);
				
				societyRepository.createDbTables(societyDbName);
				
				return 1;
			} else {
				return -1;
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			
			return 0;
		}
	}
	
}
