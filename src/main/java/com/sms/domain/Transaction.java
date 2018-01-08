package com.sms.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Transaction {
	public static final String TYPE_CASH = "CASH";
	public static final String TYPE_CHEQUE = "CHEQUE";
	public static final String TYPE_OTHER = "OTHER";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer transactionId;
	
	@Column
	private Integer subscriptionId;
	
	@Column
	private Float transactionAmount;
	
	@Column
	private TransactionType transactionType;
	
	@Column
	private String transactionDetail;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date transactionCreatedOn;
	
	@Column
	private Integer transactionCreatedBy;

	public Integer getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	public Integer getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(Integer subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public Float getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(Float transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionDetail() {
		return transactionDetail;
	}

	public void setTransactionDetail(String transactionDetail) {
		this.transactionDetail = transactionDetail;
	}

	public Date getTransactionCreatedOn() {
		return transactionCreatedOn;
	}

	public void setTransactionCreatedOn(Date transactionCreatedOn) {
		this.transactionCreatedOn = transactionCreatedOn;
	}

	public Integer getTransactionCreatedBy() {
		return transactionCreatedBy;
	}

	public void setTransactionCreatedBy(Integer transactionCreatedBy) {
		this.transactionCreatedBy = transactionCreatedBy;
	}
}
