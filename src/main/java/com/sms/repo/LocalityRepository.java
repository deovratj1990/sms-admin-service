package com.sms.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sms.domain.Locality;

public interface LocalityRepository extends CrudRepository<Locality, Long> {
	
	public List<Locality> findLocalityByPincodeId(Long id);

}
