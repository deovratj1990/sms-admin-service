package com.sms.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sms.domain.Locality;

public interface LocalityRepository extends CrudRepository<Locality, Integer> {
	
	public List<Locality> findLocalityByPincodeIdOrderByLocalityNameAsc(Integer id);
	
	public Locality findByLocalityName(String localityName);

}
