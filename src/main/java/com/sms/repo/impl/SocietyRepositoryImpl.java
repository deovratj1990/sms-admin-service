package com.sms.repo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.sms.repo.SocietyRepositoryCustom;

public class SocietyRepositoryImpl implements SocietyRepositoryCustom {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public boolean createDb(String dbName) {
		String query = "create database " + dbName;
		
		boolean created = (jdbcTemplate.update(query) != 0);

		return created;
	}

	@Override
	public boolean createDbTables(String dbName) {
		String wingTableQuery = "create table " + dbName
				+ ".wing (wing_id int(11) not null auto_increment, wing_name varchar(100) default null, society_id int(11) default null, primary key (wing_id))";
		String roomTableQuery = "create table " + dbName
				+ ".room (room_id int(11) not null auto_increment, room_number varchar(10) default null, wing_id int(11) default null, role_id tinyint(2), primary key (room_id))";
		String residentTableQuery = "CREATE TABLE `" + dbName
				+ "`.`resident` (`resident_id` int(11) NOT NULL, `room_id` int(11) DEFAULT NULL, `resident_name` varchar(100) DEFAULT NULL, `resident_mobile` varchar(13) DEFAULT NULL, `resident_email` varchar(100) DEFAULT NULL, `resident_from_date` datetime DEFAULT NULL, `resident_to_date` datetime DEFAULT NULL, `resident_status` tinyint(2) DEFAULT NULL, `role_id` int(11) DEFAULT NULL, `resident_created_on` datetime DEFAULT NULL, `resident_created_by` int(11) DEFAULT NULL, PRIMARY KEY (`resident_id`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;";
		String roleTableQuery = "CREATE TABLE `" + dbName
				+ "`.`role` (`role_id` int(11) NOT NULL, `role_name` varchar(50) DEFAULT NULL, PRIMARY KEY (`role_id`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;";
		String userTableQuery = "CREATE TABLE `" + dbName
				+ "`.`user` (`user_id` int(11) NOT NULL, `room_id` int(11) DEFAULT NULL, `role_id` int(11) DEFAULT NULL, `user_name` varchar(100) DEFAULT NULL, `user_mobile` varchar(13) DEFAULT NULL, `user_email` varchar(100) DEFAULT NULL, `user_password` varchar(100) DEFAULT NULL, `user_otp` tinyint(4) DEFAULT NULL, `user_imei` varchar(16) DEFAULT NULL, `user_status` tinyint(2) DEFAULT NULL, PRIMARY KEY (`user_id`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;";
		String accountComponentTableQuery = "CREATE TABLE `" + dbName
				+ "`.`account_component` (`account_component_id` int(11) NOT NULL, `account_component_name` varchar(100) DEFAULT NULL, `account_component_transaction_type` tinyint(2) DEFAULT NULL, `account_component_parent` int(11) DEFAULT NULL, `account_component_amount` decimal(10,0) DEFAULT NULL, `account_component_from` date DEFAULT NULL, `account_component_to` date DEFAULT NULL, `account_component_created_on` datetime DEFAULT NULL, `account_component_created_by` int(11) DEFAULT NULL, `account_component_status` tinyint(2) DEFAULT NULL, PRIMARY KEY (`account_component_id`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;";
		String accountTableQuery = "CREATE TABLE `" + dbName
				+ "`.`account` (`account_id` int(11) NOT NULL, `account_component_id` int(11) DEFAULT NULL, `room_id` int(11) DEFAULT NULL, `account_amount` decimal(10,0) DEFAULT NULL, `account_payment_for_month` tinyint(2) DEFAULT NULL, `account_payment_for_year` tinyint(4) DEFAULT NULL, `account_payment_date` datetime DEFAULT NULL, `account_created_on` datetime DEFAULT NULL, `account_created_by` int(11) DEFAULT NULL, `account_status` tinyint(2) NOT NULL, PRIMARY KEY (`account_id`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;";
		String meetingTableQuery = "CREATE TABLE `" + dbName
				+ "`.`meeting` (`meeting_id` int(11) NOT NULL, `meeting_topic` varchar(255) DEFAULT NULL, `meeting_agenda` text, `meeting_date` datetime DEFAULT NULL, `meeting_created_on` datetime DEFAULT NULL, `meeting_created_by` int(11) DEFAULT NULL, `meeting_status` tinyint(2) DEFAULT NULL, `meeting_attendee` text, PRIMARY KEY (`meeting_id`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;";
		String momTableQuery = "CREATE TABLE `" + dbName
				+ "`.`mom` (`mom_id` int(11) NOT NULL, `meeting_id` int(11) DEFAULT NULL, `mom_desc` text, `mom_img` varchar(255) DEFAULT NULL, `mom_type` tinyint(2) DEFAULT NULL, `mom_deadline` datetime DEFAULT NULL, `mom_created_on` datetime DEFAULT NULL, `mom_created_by` int(11) DEFAULT NULL, `mom_status` tinyint(2) DEFAULT NULL, PRIMARY KEY (`mom_id`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;";
		String complaintTableQuery = "CREATE TABLE `" + dbName
				+ "`.`complaint` (`complaint_id` int(11) NOT NULL, `room_id` int(11) DEFAULT NULL, `complaint_topic` varchar(255) DEFAULT NULL, `complaint_desc` text, `complaint_date` datetime DEFAULT NULL, `complaint_feedback` text, `complaint_created_on` datetime DEFAULT NULL, `complaint_modified_on` datetime DEFAULT NULL, `complaint_modified_by` int(11) DEFAULT NULL, `complaint_status` tinyint(2) DEFAULT NULL, PRIMARY KEY (`complaint_id`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;";

		int[] batchResult = jdbcTemplate.batchUpdate(wingTableQuery, roomTableQuery, residentTableQuery, roleTableQuery, userTableQuery,
				accountComponentTableQuery, accountTableQuery, meetingTableQuery, momTableQuery, complaintTableQuery);
		
		boolean created = true;
		
		for(int index = 0; index < batchResult.length; index++) {
			created = created && (batchResult[index] != 0);
		}
		
		return created;
	}

}
