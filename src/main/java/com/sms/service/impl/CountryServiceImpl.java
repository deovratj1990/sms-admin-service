package com.sms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.domain.Country;
import com.sms.repository.CountryRepository;
import com.sms.service.CountryService;

@Service
public class CountryServiceImpl implements CountryService {

	
	@Autowired
	CountryRepository countryRepository;
	
	public List<Country> getCountries() {
		List<Country> list = (List<Country>) countryRepository.findAll();
		return list;
	}

	public Country addCountry(Country country) {
		if(null == countryRepository.findByCountryName(country.getCountryName())) {
			Country savedCountry = countryRepository.save(country);
			return savedCountry;
		} else {
			return null;
		}
	}

	public Country getCountryByCountryId(Long countryId) {
		return countryRepository.findCountryByCountryId(countryId);
	}

	public Country editCountry(Country country) {
		
		return null;
	}
}
