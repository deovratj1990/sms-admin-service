package com.sms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.domain.City;
import com.sms.domain.State;
import com.sms.repo.CityRepository;
import com.sms.service.CityService;

@Service
public class CityServiceImpl implements CityService {

	@Autowired
	CityRepository cityRepository;
	
	public City save(City city) {
		return cityRepository.save(city);
	}

	public List<City> getByStateId(Integer stateId) {
		return cityRepository.findByStateIdOrderByCityNameAsc(stateId);		
	}
	
	public City getByCityId(Integer cityId) {
		return cityRepository.findOne(cityId);
	}

	public City getByCityName(String cityName) {
		return cityRepository.findByCityName(cityName);
	}
}
