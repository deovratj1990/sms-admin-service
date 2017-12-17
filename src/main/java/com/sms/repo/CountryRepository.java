package com.sms.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sms.domain.Country;

public interface CountryRepository extends CrudRepository<Country, Integer>{
	
	public List<Country> findAllByOrderByCountryNameAsc();
	
	public Country findByCountryName(String countryName);

}
