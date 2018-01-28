package com.sms.domain.constant;

import java.util.HashMap;
import java.util.Map;

public enum SocietyStatus {
	ACTIVE(1, "Active"),
	INACTIVE(2, "Inactive"),
	DELETED(3, "Deleted");
	
	private static Map<Integer, SocietyStatus> map;
	
	private final Integer value;
	private final String text;
	
	static {
		map = new HashMap<Integer, SocietyStatus>();
		
		for(SocietyStatus societyStatus : values()) {
			map.put(societyStatus.toInteger(), societyStatus);
		}
	}
	
	SocietyStatus(Integer value, String text) {
		this.value = value;
		this.text = text;
	}
	
	public static SocietyStatus parseEnum(Integer value) {
		return map.get(value);
	}
	
	public static boolean contains(Integer value) {
		return map.containsKey(value);
	}
	
	public int toInteger() {
		return value;
	}
	
	public String toString() {
		return text;
	}
}
