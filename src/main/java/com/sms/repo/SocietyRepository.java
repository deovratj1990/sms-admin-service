package com.sms.repo;

import org.springframework.data.repository.CrudRepository;

import com.sms.domain.Society;

public interface SocietyRepository extends CrudRepository<Society, Long> {
	public Society findBySocietyNameAndLocalityId(String societyName, Long localityName);
}
