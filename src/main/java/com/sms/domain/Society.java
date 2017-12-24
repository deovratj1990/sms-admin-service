package com.sms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Society {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer societyId;
	
	@Column
	private String societyName;
	
	@Column
	private Integer localityId;

	public Integer getSocietyId() {
		return societyId;
	}

	public void setSocietyId(Integer societyId) {
		this.societyId = societyId;
	}

	public String getSocietyName() {
		return societyName;
	}

	public void setSocietyName(String societyName) {
		this.societyName = societyName;
	}

	public Integer getLocalityId() {
		return localityId;
	}

	public void setLocalityId(Integer localityId) {
		this.localityId = localityId;
	}
	
}
