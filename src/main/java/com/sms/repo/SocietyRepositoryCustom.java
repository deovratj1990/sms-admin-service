package com.sms.repo;

import java.util.List;
import java.util.Map;

public interface SocietyRepositoryCustom {
	public void createDatabase(String databaseName);
	
	public void setDatabase(String databaseName);
	
	public void createTables();
	
	public Integer getWingIdByWingName(String wingName);
	
	public Integer getRoomIdByWingNameAndRoomName(String wingName, String roomNumber);
	
	public void insertRoles();
	
	public void insertWings(Integer societyId, List<String> wingDetails);
	
	public void insertRooms(Integer societyId, Integer wingId, List<Map<String, String>> roomDetails);
	
	public void insertSecretary(Map<String, Object> secretaryDetails);
}
