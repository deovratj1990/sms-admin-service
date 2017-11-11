package com.sms.service;

import java.util.List;

import com.sms.domain.City;

public interface CityService {
	
	public City saveCity(City city);
	
	public List<City> getCityByStateId(Long stateId);

}
