package com.sms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.domain.City;
import com.sms.repository.CityRepository;
import com.sms.service.CityService;

@Service
public class CityServiceImpl implements CityService {

	@Autowired
	CityRepository cityRepository;
	
	public City saveCity(City city) {
		return cityRepository.save(city);
	}

	public List<City> getCityByStateId(Long stateId) {
		return cityRepository.findCityByStateId(stateId);		
	}
}
