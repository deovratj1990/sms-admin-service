package com.sms.service;

import java.util.List;

import com.sms.domain.Country;

public interface CountryService {
	
	public List<Country> getAll();
	
	public Country add(Country country);
	
	public Country getByCountryId(Long countryId);
	
	public Country edit(Country country);
	
}
