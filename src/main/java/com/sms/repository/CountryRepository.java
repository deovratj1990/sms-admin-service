package com.sms.repository;

import org.springframework.data.repository.CrudRepository;

import com.sms.domain.Country;

public interface CountryRepository extends CrudRepository<Country, Long>{
	
	public Country findByCountryName(String countryName);
	
	public Country findCountryByCountryId(Long Id);

}
