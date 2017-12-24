package com.sms.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SubscriptionPeriod {
	public static final int TYPE_FREE = 1;
	public static final int TYPE_PAID = 2;
	
	public static final int STATUS_PENDING = 1;
	public static final int STATUS_PAYMENT_PENDING = 2;
	public static final int STATUS_PARTIAL_PAYMENT = 3;
	public static final int STATUS_INACTIVE = 4;
	public static final int STATUS_EXPIRED = 5;
	public static final int STATUS_ACTIVE = 6;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer subscriptionPeriodId;
	
	@Column
	private Integer scocietyId;
	
	@Column
	private Integer subscriptionPeriodType;
	
	@Column
	private Integer subscriptionPeriodDuration;
	
	@Column
	private Date subscriptionPeriodStartDate;
	
	@Column
	private Date subscriptionPeriodEndDate;
	
	@Column
	private Float subscriptionPeriodAmount;
	
	@Column
	private Date subscriptionPeriodCreatedOn;
	
	@Column
	private Integer subscriptionPeriodCreatedBy;
	
	@Column
	private Integer subscriptionPeriodStatus;

	public Integer getSubscriptionPeriodId() {
		return subscriptionPeriodId;
	}

	public void setSubscriptionPeriodId(Integer subscriptionPeriodId) {
		this.subscriptionPeriodId = subscriptionPeriodId;
	}

	public Integer getScocietyId() {
		return scocietyId;
	}

	public void setScocietyId(Integer scocietyId) {
		this.scocietyId = scocietyId;
	}

	public Integer getSubscriptionPeriodType() {
		return subscriptionPeriodType;
	}

	public void setSubscriptionPeriodType(Integer subscriptionPeriodType) {
		this.subscriptionPeriodType = subscriptionPeriodType;
	}

	public Integer getSubscriptionPeriodDuration() {
		return subscriptionPeriodDuration;
	}

	public void setSubscriptionPeriodDuration(Integer subscriptionPeriodDuration) {
		this.subscriptionPeriodDuration = subscriptionPeriodDuration;
	}

	public Date getSubscriptionPeriodStartDate() {
		return subscriptionPeriodStartDate;
	}

	public void setSubscriptionPeriodStartDate(Date subscriptionPeriodStartDate) {
		this.subscriptionPeriodStartDate = subscriptionPeriodStartDate;
	}

	public Date getSubscriptionPeriodEndDate() {
		return subscriptionPeriodEndDate;
	}

	public void setSubscriptionPeriodEndDate(Date subscriptionPeriodEndDate) {
		this.subscriptionPeriodEndDate = subscriptionPeriodEndDate;
	}

	public Float getSubscriptionPeriodAmount() {
		return subscriptionPeriodAmount;
	}

	public void setSubscriptionPeriodAmount(Float subscriptionPeriodAmount) {
		this.subscriptionPeriodAmount = subscriptionPeriodAmount;
	}

	public Date getSubscriptionPeriodCreatedOn() {
		return subscriptionPeriodCreatedOn;
	}

	public void setSubscriptionPeriodCreatedOn(Date subscriptionPeriodCreatedOn) {
		this.subscriptionPeriodCreatedOn = subscriptionPeriodCreatedOn;
	}

	public Integer getSubscriptionPeriodCreatedBy() {
		return subscriptionPeriodCreatedBy;
	}

	public void setSubscriptionPeriodCreatedBy(Integer subscriptionPeriodCreatedBy) {
		this.subscriptionPeriodCreatedBy = subscriptionPeriodCreatedBy;
	}

	public Integer getSubscriptionPeriodStatus() {
		return subscriptionPeriodStatus;
	}

	public void setSubscriptionPeriodStatus(Integer subscriptionPeriodStatus) {
		this.subscriptionPeriodStatus = subscriptionPeriodStatus;
	}
}
