package com.sms.payload.request;

import java.util.List;

public class SocietyRegister {
	private String societyName;
	
	private int countryId;
	
	private int stateId;
	
	private int cityId;
	
	private int areaId;
	
	private int pincodeId;
	
	private int localityId;
	
	private List<String> wingName;
	
	private List<String> roomName;
	
	private String secretaryWing;
	
	private String secretaryRoom;
	
	private String secretaryMobile;
	
	private int subscriptionType;
	
	private int subscriptionDuration;
	
	private float subscriptionAmount;
	
	private float transactionAmount;
	
	private int transactionType;
	
	private String transactionDetail;

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

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
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

	public int getSubscriptionType() {
		return subscriptionType;
	}

	public void setSubscriptionType(int subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	public int getSubscriptionDuration() {
		return subscriptionDuration;
	}

	public void setSubscriptionDuration(int subscriptionDuration) {
		this.subscriptionDuration = subscriptionDuration;
	}

	public float getSubscriptionAmount() {
		return subscriptionAmount;
	}

	public void setSubscriptionAmount(float subscriptionAmount) {
		this.subscriptionAmount = subscriptionAmount;
	}

	public float getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(float transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public int getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(int transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionDetail() {
		return transactionDetail;
	}

	public void setTransactionDetail(String transactionDetail) {
		this.transactionDetail = transactionDetail;
	}
}
