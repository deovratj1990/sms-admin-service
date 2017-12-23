package com.sms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.domain.Area;
import com.sms.repo.AreaRepository;
import com.sms.service.AreaService;

@Service
public class AreaServiceImpl implements AreaService {

	@Autowired
	AreaRepository areaRepository;
	
	public Area save(Area area) {
		return areaRepository.save(area);
	}

	public List<Area> getByCityId(Integer cityId) {
		return areaRepository.findByCityIdOrderByAreaNameAsc(cityId);		
	}
	
	public Area getByAreaId(Integer areaId) {
		return areaRepository.findOne(areaId);
	}

	public Area getByAreaName(String areaName) {
		return areaRepository.findByAreaName(areaName);
	}
}
