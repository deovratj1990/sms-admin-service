package com.sms.service;

import java.util.List;

import com.sms.domain.Country;

public interface CountryService {
	
	public List<Country> getAll();
	
	public Country save(Country country);

	public Country getByCountryName(String countryName);
	
	public Country getByCountryId(Integer countryId);
	
}
