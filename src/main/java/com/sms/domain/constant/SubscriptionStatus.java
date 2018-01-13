package com.sms.domain.constant;

import java.util.HashMap;
import java.util.Map;

public enum SubscriptionStatus {
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
			map.put(subscriptionStatus.toInteger(), subscriptionStatus);
		}
	}
	
	SubscriptionStatus(Integer value, String text) {
		this.value = value;
		this.text = text;
	}
	
	public static SubscriptionStatus parseEnum(Integer value) {
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
