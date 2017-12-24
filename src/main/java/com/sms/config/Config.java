package com.sms.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.web.context.WebApplicationContext;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.sms.domain.User;

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
	
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
	
	@Bean
	public DataSourceTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
	
	@Bean
	@Scope(WebApplicationContext.SCOPE_REQUEST)
	public User user() {
		return new User();
	}
	
}
