package com.sms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.domain.Country;
import com.sms.repo.CountryRepository;
import com.sms.service.CountryService;

@Service
public class CountryServiceImpl implements CountryService {

	@Autowired
	CountryRepository countryRepository;
	
	public List<Country> getAll() {
		List<Country> list = (List<Country>) countryRepository.findAll();
		return list;
	}

	public Country add(Country country) {
		if(null == countryRepository.findByCountryName(country.getCountryName())) {
			Country savedCountry = countryRepository.save(country);
			return savedCountry;
		} else {
			return null;
		}
	}

	public Country getByCountryId(Long countryId) {
		return countryRepository.findCountryByCountryId(countryId);
	}

	public Country edit(Country country) {
		
		Country existingCountry = countryRepository.findCountryByCountryId(country.getCountryId());
		
		if(null != existingCountry) {
			Country foundCountry = countryRepository.findByCountryName(country.getCountryName());
			
			if(null == foundCountry) {
				existingCountry.setCountryName(country.getCountryName());
				return countryRepository.save(existingCountry);
			}	
		}
	
		return null;
	}
}
