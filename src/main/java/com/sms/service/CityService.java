package com.sms.service;

import java.util.List;

import com.sms.domain.City;

public interface CityService {
	
	public City add(City city);
	
	public List<City> getByStateId(Integer stateId);

}
