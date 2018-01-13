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
	
	@Column
	private Integer societyWingCount;
	
	@Column
	private Integer societyRoomCount;

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

	public Integer getSocietyWingCount() {
		return societyWingCount;
	}

	public void setSocietyWingCount(Integer societyWingCount) {
		this.societyWingCount = societyWingCount;
	}

	public Integer getSocietyRoomCount() {
		return societyRoomCount;
	}

	public void setSocietyRoomCount(Integer societyRoomCount) {
		this.societyRoomCount = societyRoomCount;
	}
	
}
