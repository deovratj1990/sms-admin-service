package com.sms.service;

import java.util.List;

import com.sms.domain.City;
import com.sms.domain.State;

public interface CityService {
	
	public City save(City city);
	
	public List<City> getByStateId(Integer stateId);

	public City getByCityId(Integer cityId);
	
	public City getByCityName(String cityName);
}
