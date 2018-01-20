package com.sms.domain.constant;

import java.util.HashMap;
import java.util.Map;

public enum TransactionStatus {
	PENDING(1, "Pending"),
	SUCCESSFUL(2, "Successful"),
	FAILED(3, "Failed");
	
	private static Map<Integer, TransactionStatus> map;
	
	private final Integer value;
	private final String text;
	
	static {
		map = new HashMap<Integer, TransactionStatus>();
		
		for(TransactionStatus transactionStatus : values()) {
			map.put(transactionStatus.toInteger(), transactionStatus);
		}
	}
	
	TransactionStatus(Integer value, String text) {
		this.value = value;
		this.text = text;
	}
	
	public static TransactionStatus parseEnum(Integer value) {
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
