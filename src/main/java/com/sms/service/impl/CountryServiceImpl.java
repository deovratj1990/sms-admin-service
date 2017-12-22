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
		List<Country> list = (List<Country>) countryRepository.findAllByOrderByCountryNameAsc();
		return list;
	}

	public Country save(Country country) {
		return countryRepository.save(country);
	}

	public Country getByCountryId(Integer countryId) {
		return countryRepository.findOne(countryId);
	}

	@Override
	public Country getByCountryName(String countryName) {
		return countryRepository.findByCountryName(countryName);
	}
}
