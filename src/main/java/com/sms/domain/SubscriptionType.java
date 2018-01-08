package com.sms.domain;

import java.util.HashMap;
import java.util.Map;

public enum SubscriptionType {
	INVALID(0, "Invalid"),
	FREE(1, "Free"),
	PAID(2, "Paid");
	
	private static Map<Integer, SubscriptionType> map;
	
	private final Integer value;
	private final String text;
	
	static {
		map = new HashMap<Integer, SubscriptionType>();
		
		for(SubscriptionType subscriptionType : values()) {
			map.put(subscriptionType.getValue(), subscriptionType);
		}
	}
	
	SubscriptionType(Integer value, String text) {
		this.value = value;
		this.text = text;
	}
	
	public static boolean exists(Integer value) {
		return map.containsKey(value);
	}
	
	public static SubscriptionType getFor(Integer value) {
		return map.get(value);
	}
	
	public int getValue() {
		return value;
	}
	
	public String getText() {
		return text;
	}
}
