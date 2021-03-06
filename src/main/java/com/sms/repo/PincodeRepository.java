package com.sms.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sms.domain.Pincode;

public interface PincodeRepository extends CrudRepository<Pincode, Integer>{
	
	public List<Pincode> findPincodeByAreaIdOrderByPincodeNameAsc(Integer id);
	
	public Pincode findByPincodeName(String pincodeName);
	
}
