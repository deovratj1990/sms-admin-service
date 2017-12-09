package com.sms.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sms.domain.Pincode;

public interface PincodeRepository extends CrudRepository<Pincode, Long>{
	
	public List<Pincode> findPincodeByCityIdOrderByPincodeNameAsc(Long id);
	
}
