package com.sms.repo;

public interface SocietyDbRepository {
	public boolean createDb(String dbName);
	
	public boolean createTables(String dbName);
}
