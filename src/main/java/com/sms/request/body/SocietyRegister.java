package com.sms.request.body;

import java.util.List;

public class SocietyRegister {
	private String societyName;
	
	private int countryId;
	
	private int stateId;
	
	private int cityId;
	
	private int pincodeId;
	
	private int localityId;
	
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

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getPincodeId() {
		return pincodeId;
	}

	public void setPincodeId(int pincodeId) {
		this.pincodeId = pincodeId;
	}

	public int getLocalityId() {
		return localityId;
	}

	public void setLocalityId(int localityId) {
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
