package com.sms.service;

import java.util.List;

import com.sms.domain.Country;

public interface CountryService {
	
	public List<Country> getCountries();
	
	public Country addCountry(Country country);
	
	public Country getCountryByCountryId(Long countryId);
	
	public Country editCountry(Country country);
	
}
