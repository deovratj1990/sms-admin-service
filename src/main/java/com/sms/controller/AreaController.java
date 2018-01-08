package com.sms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sms.domain.Area;
import com.sms.payload.request.AreaSave;
import com.sms.service.AreaService;

@RestController
@RequestMapping(path="/area")
@CrossOrigin(origins="*")
public class AreaController {

	@Autowired
	AreaService areaService;
	
	@RequestMapping(path = "/save", method=RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<Map> save(@RequestBody AreaSave requestBody) {
		
		Area area = new Area();		
		Boolean validated = true;
		
		Map response = new HashMap();
		Map messages = new HashMap();
		Map data = new HashMap();
		
		response.put("messages", messages);
		response.put("data", data);
		
		if(0 != requestBody.getAreaId()) {
			area.setAreaId(requestBody.getAreaId());
		}

		area.setCityId(requestBody.getCityId());
		area.setAreaName(requestBody.getAreaName());
		
		if(area.getAreaName().equals("")) {
			validated = false;
			messages.put("areaName", "Area cannot be blank!");
		}
		
		if(!validated) {
			return new ResponseEntity<Map>(response, HttpStatus.BAD_REQUEST);
		} else {
			if(null == areaService.getByAreaName(area.getAreaName())) {
				area = areaService.save(area);
				data.put("areaId", area.getAreaId());
				return new ResponseEntity<Map>(response, HttpStatus.OK);
			} else {
				return new ResponseEntity<Map>(response, HttpStatus.CONFLICT);
			}
		}
	}
	
	@RequestMapping(path = "/getByCityId", method=RequestMethod.GET)
	public ResponseEntity<List<Area>> getByCityId(@RequestParam Integer cityId) {
		List<Area> areaList = areaService.getByCityId(cityId);
		
		if(areaList.size() != 0) {
			return new ResponseEntity<List<Area>>(areaList, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Area>>(HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping(path="/getByAreaId", method=RequestMethod.GET)
	public ResponseEntity<Area> getByAreaId(@RequestParam Integer areaId) {
		Area area = areaService.getByAreaId(areaId);
		
		if(area != null) {
			return new ResponseEntity<Area>(area, HttpStatus.OK);
		} else {
			return new ResponseEntity<Area>(HttpStatus.NO_CONTENT);
		}
	}
	
}
