package com.sms.request.body;

import java.util.List;

public class SocietyRegister {
	private String societyName;
	
	private long countryId;
	
	private long stateId;
	
	private long cityId;
	
	private long pincodeId;
	
	private long localityId;
	
	private List<String> wingName;
	
	private List<String> roomName;
	
	private String secretaryWing;
	
	private String secretaryRoom;
	
	private String secretaryMobile;

	public String getSocietyName() {
		return societyName;
	}

	public void setSocietyName(String societyName) {
		this.societyName = societyName;
	}

	public long getCountryId() {
		return countryId;
	}

	public void setCountryId(long countryId) {
		this.countryId = countryId;
	}

	public long getStateId() {
		return stateId;
	}

	public void setStateId(long stateId) {
		this.stateId = stateId;
	}

	public long getCityId() {
		return cityId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
	}

	public long getPincodeId() {
		return pincodeId;
	}

	public void setPincodeId(long pincodeId) {
		this.pincodeId = pincodeId;
	}

	public long getLocalityId() {
		return localityId;
	}

	public void setLocalityId(long localityId) {
		this.localityId = localityId;
	}

	public List<String> getWingName() {
		return wingName;
	}

	public void setWingName(List<String> wingName) {
		this.wingName = wingName;
	}

	public List<String> getRoomName() {
		return roomName;
	}

	public void setRoomName(List<String> roomName) {
		this.roomName = roomName;
	}

	public String getSecretaryWing() {
		return secretaryWing;
	}

	public void setSecretaryWing(String secretaryWing) {
		this.secretaryWing = secretaryWing;
	}

	public String getSecretaryRoom() {
		return secretaryRoom;
	}

	public void setSecretaryRoom(String secretaryRoom) {
		this.secretaryRoom = secretaryRoom;
	}

	public String getSecretaryMobile() {
		return secretaryMobile;
	}

	public void setSecretaryMobile(String secretaryMobile) {
		this.secretaryMobile = secretaryMobile;
	}
}
