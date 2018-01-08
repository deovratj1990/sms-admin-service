package com.sms.payload.request;

public class SubscriptionSave {
	private Integer subscriptionId;
	
	private Integer subscriptionDuration;
	
	private Integer subscriptionType;
	
	private Integer subscriptionStatus;
	
	private Float subscriptionAmount;
	
	private Float transactionAmount;
	
	private Integer transactionType;
	
	private String transactionDetail;

	public Integer getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(Integer subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public Integer getSubscriptionDuration() {
		return subscriptionDuration;
	}

	public void setSubscriptionDuration(Integer subscriptionDuration) {
		this.subscriptionDuration = subscriptionDuration;
	}

	public Integer getSubscriptionType() {
		return subscriptionType;
	}

	public void setSubscriptionType(Integer subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	public Integer getSubscriptionStatus() {
		return subscriptionStatus;
	}

	public void setSubscriptionStatus(Integer subscriptionStatus) {
		this.subscriptionStatus = subscriptionStatus;
	}

	public Float getSubscriptionAmount() {
		return subscriptionAmount;
	}

	public void setSubscriptionAmount(Float subscriptionAmount) {
		this.subscriptionAmount = subscriptionAmount;
	}

	public Float getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(Float transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public Integer getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(Integer transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionDetail() {
		return transactionDetail;
	}

	public void setTransactionDetail(String transactionDetail) {
		this.transactionDetail = transactionDetail;
	}
}
