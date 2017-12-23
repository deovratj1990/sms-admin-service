package com.sms.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sms.domain.Area;

public interface AreaRepository extends CrudRepository<Area, Integer>{
	
	public List<Area> findByCityIdOrderByAreaNameAsc(Integer id);
	
	public Area findByAreaName(String areaName);

}
