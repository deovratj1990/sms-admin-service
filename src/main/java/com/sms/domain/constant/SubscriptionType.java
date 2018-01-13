package com.sms.domain.constant;

import java.util.HashMap;
import java.util.Map;

public enum SubscriptionType {
	FREE(1, "Free"),
	PAID(2, "Paid");
	
	private static Map<Integer, SubscriptionType> map;
	
	private final Integer value;
	private final String text;
	
	static {
		map = new HashMap<Integer, SubscriptionType>();
		
		for(SubscriptionType subscriptionType : values()) {
			map.put(subscriptionType.toInteger(), subscriptionType);
		}
	}
	
	SubscriptionType(Integer value, String text) {
		this.value = value;
		this.text = text;
	}
	
	public static SubscriptionType parseEnum(Integer value) {
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
