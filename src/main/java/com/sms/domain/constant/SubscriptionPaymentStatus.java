package com.sms.domain.constant;

import java.util.HashMap;
import java.util.Map;

public enum SubscriptionPaymentStatus {
	NA(1, "NA"),
	PENDING(2, "Pending"),
	PARTIAL(3, "Partial"),
	PAID(4, "Paid"),
	NOT_PAID(5, "Not Paid");
	
	private static Map<Integer, SubscriptionPaymentStatus> map;
	
	private final Integer value;
	private final String text;
	
	static {
		map = new HashMap<Integer, SubscriptionPaymentStatus>();
		
		for(SubscriptionPaymentStatus subscriptionPaymentStatus : values()) {
			map.put(subscriptionPaymentStatus.toInteger(), subscriptionPaymentStatus);
		}
	}
	
	SubscriptionPaymentStatus(Integer value, String text) {
		this.value = value;
		this.text = text;
	}
	
	public static SubscriptionPaymentStatus parseEnum(Integer value) {
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
