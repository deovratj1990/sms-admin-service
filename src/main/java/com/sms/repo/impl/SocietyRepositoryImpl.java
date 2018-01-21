package com.sms.repo.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.sms.repo.SocietyRepositoryCustom;

public class SocietyRepositoryImpl implements SocietyRepositoryCustom {
	
	private String databaseName;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void createDatabase(String databaseName) {
		jdbcTemplate.update("create database " + databaseName);
	}
	
	@Override
	public void setDatabase(String databaseName) {
		this.databaseName = databaseName;
	}

	@Override
	public void createTables() {
		String wingTableQuery = "create table " + databaseName
				+ ".wing (wing_id int(11) not null auto_increment, wing_name varchar(100) default null, society_id int(11) default null, primary key (wing_id))";
		String roomTableQuery = "create table " + databaseName
				+ ".room (room_id int(11) not null auto_increment, room_number varchar(10) default null, wing_id int(11) default null, role_id tinyint(2) default null, primary key (room_id))";
		String residentTableQuery = "CREATE TABLE `" + databaseName
				+ "`.`resident` (`resident_id` int(11) NOT NULL AUTO_INCREMENT, `room_id` int(11) DEFAULT NULL, `resident_name` varchar(100) DEFAULT NULL, `resident_mobile` varchar(13) DEFAULT NULL, `resident_email` varchar(100) DEFAULT NULL, `resident_from_date` datetime DEFAULT NULL, `resident_to_date` datetime DEFAULT NULL, `resident_status` tinyint(2) DEFAULT NULL, `role_id` int(11) DEFAULT NULL, `resident_created_on` datetime DEFAULT NULL, `resident_created_by` int(11) DEFAULT NULL, PRIMARY KEY (`resident_id`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;";
		String roleTableQuery = "CREATE TABLE `" + databaseName
				+ "`.`role` (`role_id` int(11) NOT NULL AUTO_INCREMENT, `role_name` varchar(50) DEFAULT NULL, PRIMARY KEY (`role_id`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;";
		String userTableQuery = "CREATE TABLE `" + databaseName
				+ "`.`user` (`user_id` int(11) NOT NULL AUTO_INCREMENT, `room_id` int(11) DEFAULT NULL, `role_id` int(11) DEFAULT NULL, `user_name` varchar(100) DEFAULT NULL, `user_mobile` varchar(13) DEFAULT NULL, `user_email` varchar(100) DEFAULT NULL, `user_password` varchar(100) DEFAULT NULL, `user_otp` tinyint(4) DEFAULT NULL, `user_imei` varchar(16) DEFAULT NULL, `user_status` tinyint(2) DEFAULT NULL, PRIMARY KEY (`user_id`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;";
		String accountComponentTableQuery = "CREATE TABLE `" + databaseName
				+ "`.`account_component` (`account_component_id` int(11) NOT NULL AUTO_INCREMENT, `account_component_name` varchar(100) DEFAULT NULL, `account_component_transaction_type` tinyint(2) DEFAULT NULL, `account_component_parent` int(11) DEFAULT NULL, `account_component_amount` decimal(10,0) DEFAULT NULL, `account_component_from` date DEFAULT NULL, `account_component_to` date DEFAULT NULL, `account_component_created_on` datetime DEFAULT NULL, `account_component_created_by` int(11) DEFAULT NULL, `account_component_status` tinyint(2) DEFAULT NULL, PRIMARY KEY (`account_component_id`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;";
		String accountTableQuery = "CREATE TABLE `" + databaseName
				+ "`.`account` (`account_id` int(11) NOT NULL AUTO_INCREMENT, `account_component_id` int(11) DEFAULT NULL, `room_id` int(11) DEFAULT NULL, `account_amount` decimal(10,0) DEFAULT NULL, `account_payment_for_month` tinyint(2) DEFAULT NULL, `account_payment_for_year` tinyint(4) DEFAULT NULL, `account_payment_date` datetime DEFAULT NULL, `account_created_on` datetime DEFAULT NULL, `account_created_by` int(11) DEFAULT NULL, `account_status` tinyint(2) NOT NULL, PRIMARY KEY (`account_id`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;";
		String meetingTableQuery = "CREATE TABLE `" + databaseName
				+ "`.`meeting` (`meeting_id` int(11) NOT NULL AUTO_INCREMENT, `meeting_topic` varchar(255) DEFAULT NULL, `meeting_agenda` text, `meeting_date` datetime DEFAULT NULL, `meeting_created_on` datetime DEFAULT NULL, `meeting_created_by` int(11) DEFAULT NULL, `meeting_status` tinyint(2) DEFAULT NULL, `meeting_attendee` text, PRIMARY KEY (`meeting_id`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;";
		String momTableQuery = "CREATE TABLE `" + databaseName
				+ "`.`mom` (`mom_id` int(11) NOT NULL AUTO_INCREMENT, `meeting_id` int(11) DEFAULT NULL, `mom_desc` text, `mom_img` varchar(255) DEFAULT NULL, `mom_type` tinyint(2) DEFAULT NULL, `mom_deadline` datetime DEFAULT NULL, `mom_created_on` datetime DEFAULT NULL, `mom_created_by` int(11) DEFAULT NULL, `mom_status` tinyint(2) DEFAULT NULL, PRIMARY KEY (`mom_id`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;";
		String complaintTableQuery = "CREATE TABLE `" + databaseName
				+ "`.`complaint` (`complaint_id` int(11) NOT NULL AUTO_INCREMENT, `room_id` int(11) DEFAULT NULL, `complaint_topic` varchar(255) DEFAULT NULL, `complaint_desc` text, `complaint_date` datetime DEFAULT NULL, `complaint_feedback` text, `complaint_created_on` datetime DEFAULT NULL, `complaint_modified_on` datetime DEFAULT NULL, `complaint_modified_by` int(11) DEFAULT NULL, `complaint_status` tinyint(2) DEFAULT NULL, PRIMARY KEY (`complaint_id`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;";

		jdbcTemplate.batchUpdate(wingTableQuery, roomTableQuery, residentTableQuery, roleTableQuery, userTableQuery, accountComponentTableQuery, accountTableQuery, meetingTableQuery, momTableQuery, complaintTableQuery);
	}
	
	@Override
	public Integer getWingIdByWingName(String wingName) {
		return jdbcTemplate.queryForObject("SELECT `wing_id` FROM `" + databaseName + "`.`wing` WHERE wing_name = ?", Integer.class, wingName);
	}
	
	@Override
	public Integer getRoomIdByWingNameAndRoomName(String wingName, String roomNumber) {
		return jdbcTemplate.queryForObject("SELECT `r`.`room_id` FROM `" + databaseName + "`.`room` `r` INNER JOIN `" + databaseName + "`.`wing` `w` ON `w`.`wing_id` = `r`.`wing_id` WHERE `w`.`wing_name` = ? AND `r`.`room_number` = ?;", Integer.class, wingName, roomNumber);
	}
	
	@Override
	public void insertRoles() {
		jdbcTemplate.update("INSERT INTO `" + databaseName + "`.`role` (`role_id`, `role_name`) VALUES (null, 'Secretary'), (null, 'Chairman'), (null, 'Treasurer'), (null, 'Committee Member'), (null, 'Member'), (null, 'Resident'), (null, 'Tenant');");
	}

	@Override
	public void insertWings(Integer societyId, List<String> wingDetails) {
		List<Object[]> wingValuesList = new ArrayList<Object[]>();
		int[] wingValuesTypes = new int[] {Types.VARCHAR, Types.INTEGER};
		
		for(String wingName : wingDetails) {
			Object[] wingValues = new Object[2];
			
			wingValues[0] = wingName;
			wingValues[1] = societyId;
			
			wingValuesList.add(wingValues);
		}
		
		jdbcTemplate.batchUpdate("INSERT INTO `" + databaseName + "`.`wing` (`wing_id`, `wing_name`, `society_id`) VALUES (null, ?, ?);", wingValuesList, wingValuesTypes);
	}
	
	@Override
	public void insertRooms(Integer societyId, Integer wingId, List<Map<String, String>> roomDetails) {
		List<Object[]> roomValuesList = new ArrayList<Object[]>();
		int[] roomValuesTypes = new int[] {Types.VARCHAR, Types.INTEGER, Types.INTEGER};
		
		for(Map<String, String> room : roomDetails) {
			Object[] roomValues = new Object[3];
			
			roomValues[0] = room.get("number");
			roomValues[1] = wingId;
			roomValues[2] = room.get("role");
			
			roomValuesList.add(roomValues);
		}
		
		jdbcTemplate.batchUpdate("INSERT INTO `" + databaseName + "`.`room` (`room_id`, `room_number`, `wing_id`, `role_id`) VALUES (null, ?, ?, ?);", roomValuesList, roomValuesTypes);
	}
	
	@Override
	public void insertSecretary(Map<String, Object> secretaryDetails) {
		jdbcTemplate.update("INSERT INTO `" + databaseName + "`.`user` (`user_id`, `room_id`, `role_id`, `user_mobile`, `user_status`) VALUES (null, ?, ?, ?, 1);", secretaryDetails.get("room"), secretaryDetails.get("role"), secretaryDetails.get("mobile"));
	}
}
