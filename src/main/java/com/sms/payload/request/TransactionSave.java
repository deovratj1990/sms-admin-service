package com.sms.payload.request;

public class TransactionSave {
	private Integer subscriptionId;
	
	private Integer transactionType;
	
	private Float transactionAmount;
	
	private String transactionDetail;

	public Integer getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(Integer subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public Integer getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(Integer transactionType) {
		this.transactionType = transactionType;
	}

	public Float getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(Float transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String getTransactionDetail() {
		return transactionDetail;
	}

	public void setTransactionDetail(String transactionDetail) {
		this.transactionDetail = transactionDetail;
	}
}
