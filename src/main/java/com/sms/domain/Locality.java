package com.sms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Locality {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer localityId;
	
	@Column
	private String localityName;
	
	@Column
	private Integer pincodeId;

	public Integer getLocalityId() {
		return localityId;
	}

	public void setLocalityId(Integer localityId) {
		this.localityId = localityId;
	}

	public String getLocalityName() {
		return localityName;
	}

	public void setLocalityName(String localityName) {
		this.localityName = localityName;
	}

	public Integer getPincodeId() {
		return pincodeId;
	}

	public void setPincodeId(Integer pincodeId) {
		this.pincodeId = pincodeId;
	}
	
	

}
