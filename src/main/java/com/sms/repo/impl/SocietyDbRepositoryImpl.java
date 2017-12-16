package com.sms.repo.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.sms.repo.SocietyDbRepository;

@Repository
public class SocietyDbRepositoryImpl implements SocietyDbRepository {
	
	@Autowired
	private MysqlDataSource ds;

	@Override
	public boolean createDb(String dbName) {
		
		Connection conn = null;
		Statement stmt = null;
		
		boolean created = false;
		
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			
			String query = "create database " + dbName;
			
			created = (stmt.executeUpdate(query) != 0);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return created;
	}

	@Override
	public boolean createTables(String dbName) {
		
		Connection conn = null;
		Statement stmt = null;
		
		boolean created = false;
		
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			
			String query = "create table " + dbName + ".wing (wing_id bigint(20) not null auto_increment, wing_name varchar(255) default null, society_id bigint(20) default null, primary key (wing_id))";
			
			created = (stmt.executeUpdate(query) == 0);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return created;
	}
	
}
