package com.sms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tbl_pincode")
public class Pincode {	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="pincode_id")
	private Long pincodeId;
	
	@Column(name="pincode_name")
	private String pincodeName;

	@Column(name="city_id")
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
