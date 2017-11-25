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
	private Long pincodeId;
	
	@Column
	private String pincodeName;

	@Column
	private Long cityId;
	
	public Long getPincodeId() {
		return pincodeId;
	}

	public void setPincodeId(Long pincodeId) {
		this.pincodeId = pincodeId;
	}

	public String getPincodeName() {
		return pincodeName;
	}

	public void setPincodeName(String pincodeName) {
		this.pincodeName = pincodeName;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

}
