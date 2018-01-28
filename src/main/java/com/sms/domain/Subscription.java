package com.sms.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sms.domain.constant.SubscriptionPaymentStatus;
import com.sms.domain.constant.SubscriptionStatus;
import com.sms.domain.constant.SubscriptionType;
import com.sms.domain.converter.SubscriptionPaymentStatusConverter;
import com.sms.domain.converter.SubscriptionStatusConverter;
import com.sms.domain.converter.SubscriptionTypeConverter;

@Entity
public class Subscription {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer subscriptionId;
	
	@Column
	private Integer societyId;
	
	@Column
	@Convert(converter = SubscriptionTypeConverter.class)
	private SubscriptionType subscriptionType;
	
	@Column
	private Integer subscriptionDuration;
	
	@Column
	private Float subscriptionAmount;
	
	@Column
	@Temporal(TemporalType.DATE)
	private Date subscriptionStartDate;
	
	@Column
	@Temporal(TemporalType.DATE)
	private Date subscriptionEndDate;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date subscriptionCreatedOn;
	
	@Column
	private Integer subscriptionCreatedBy;
	
	@Column
	@Convert(converter = SubscriptionStatusConverter.class)
	private SubscriptionStatus subscriptionStatus;
	
	@Column
	@Convert(converter = SubscriptionPaymentStatusConverter.class)
	private SubscriptionPaymentStatus subscriptionPaymentStatus;

	public Integer getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(Integer subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public Integer getSocietyId() {
		return societyId;
	}

	public void setSocietyId(Integer societyId) {
		this.societyId = societyId;
	}

	public SubscriptionType getSubscriptionType() {
		return subscriptionType;
	}

	public void setSubscriptionType(SubscriptionType subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	public Integer getSubscriptionDuration() {
		return subscriptionDuration;
	}

	public void setSubscriptionDuration(Integer subscriptionDuration) {
		this.subscriptionDuration = subscriptionDuration;
	}

	public Float getSubscriptionAmount() {
		return subscriptionAmount;
	}

	public void setSubscriptionAmount(Float subscriptionAmount) {
		this.subscriptionAmount = subscriptionAmount;
	}

	public Date getSubscriptionStartDate() {
		return subscriptionStartDate;
	}

	public void setSubscriptionStartDate(Date subscriptionStartDate) {
		this.subscriptionStartDate = subscriptionStartDate;
	}

	public Date getSubscriptionEndDate() {
		return subscriptionEndDate;
	}

	public void setSubscriptionEndDate(Date subscriptionEndDate) {
		this.subscriptionEndDate = subscriptionEndDate;
	}

	public Date getSubscriptionCreatedOn() {
		return subscriptionCreatedOn;
	}

	public void setSubscriptionCreatedOn(Date subscriptionCreatedOn) {
		this.subscriptionCreatedOn = subscriptionCreatedOn;
	}

	public Integer getSubscriptionCreatedBy() {
		return subscriptionCreatedBy;
	}

	public void setSubscriptionCreatedBy(Integer subscriptionCreatedBy) {
		this.subscriptionCreatedBy = subscriptionCreatedBy;
	}

	public SubscriptionStatus getSubscriptionStatus() {
		return subscriptionStatus;
	}

	public void setSubscriptionStatus(SubscriptionStatus subscriptionStatus) {
		this.subscriptionStatus = subscriptionStatus;
	}

	public SubscriptionPaymentStatus getSubscriptionPaymentStatus() {
		return subscriptionPaymentStatus;
	}

	public void setSubscriptionPaymentStatus(SubscriptionPaymentStatus subscriptionPaymentStatus) {
		this.subscriptionPaymentStatus = subscriptionPaymentStatus;
	}
}
