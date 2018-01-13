package com.sms.repo;

import java.util.List;
import java.util.Map;

public interface SocietyRepositoryCustom {
	public boolean createDb(String dbName);
	
	public boolean createDbTables(String dbName);
	
	public boolean initializeDbTables(String dbName, Map<String, List<String>> wingNameMap);
}
