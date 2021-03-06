package com.sms.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sms.domain.City;

public interface CityRepository extends CrudRepository<City, Integer>{
	
	public List<City> findByStateIdOrderByCityNameAsc(Integer id);
	
	public City findByCityName(String cityName);

}
