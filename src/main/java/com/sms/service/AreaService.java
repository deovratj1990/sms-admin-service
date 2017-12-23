package com.sms.service;

import java.util.List;

import com.sms.domain.Area;

public interface AreaService {
	
	public Area save(Area area);
	
	public List<Area> getByCityId(Integer cityId);

	public Area getByAreaId(Integer areaId);
	
	public Area getByAreaName(String areaName);
}
