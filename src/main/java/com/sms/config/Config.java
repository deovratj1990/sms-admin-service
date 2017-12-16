package com.sms.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mysql.cj.jdbc.MysqlDataSource;

@Configuration
public class Config {
	
	@Value("${sms.database.serverName}")
	private String databaseServerName;
	
	@Value("${sms.database.serverPort}")
	private int databaseServerPort;
	
	@Value("${sms.database.user}")
	private String databaseUser;
	
	@Value("${sms.database.password}")
	private String databasePassword;
	
	@Value("${sms.database.name}")
	private String databaseName;

	@Bean
	public DataSource dataSource() {
		MysqlDataSource dataSource = new MysqlDataSource();
		
		dataSource.setServerName(databaseServerName);
		dataSource.setPort(databaseServerPort);
		dataSource.setUser(databaseUser);
		dataSource.setPassword(databasePassword);
		dataSource.setDatabaseName(databaseName);
		
		return dataSource;
	}
	
}
