package com.sms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Pincode {	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Integer pincodeId;
	
	@Column
	private String pincodeName;

	@Column
	private Integer areaId;
	
	public Integer getPincodeId() {
		return pincodeId;
	}

	public void setPincodeId(Integer pincodeId) {
		this.pincodeId = pincodeId;
	}

	public String getPincodeName() {
		return pincodeName;
	}

	public void setPincodeName(String pincodeName) {
		this.pincodeName = pincodeName;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

}
