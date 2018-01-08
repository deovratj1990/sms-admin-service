package com.sms.domain;

import java.util.HashMap;
import java.util.Map;

public enum SubscriptionStatus {
	INVALID(0, "Invalid Status"),
	PAYMENT_PENDING(1, "Payment Pending"),
	PARTIAL_PAYMENT(2, "Partial Payment"),
	INACTIVE(3, "Inactive"),
	EXPIRED(4, "Expired"),
	ACTIVE(5, "Active");
	
	private static Map<Integer, SubscriptionStatus> map;
	
	private final Integer value;
	private final String text;
	
	static {
		map = new HashMap<Integer, SubscriptionStatus>();
		
		for(SubscriptionStatus subscriptionStatus : values()) {
			map.put(subscriptionStatus.getValue(), subscriptionStatus);
		}
	}
	
	SubscriptionStatus(Integer value, String text) {
		this.value = value;
		this.text = text;
	}
	
	public static boolean exists(Integer value) {
		return map.containsKey(value);
	}
	
	public static SubscriptionStatus getFor(Integer value) {
		return map.get(value);
	}
	
	public int getValue() {
		return value;
	}
	
	public String getText() {
		return text;
	}
}
