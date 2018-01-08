package com.sms.domain;

import java.util.HashMap;
import java.util.Map;

public enum TransactionType {
	INVALID(0, "Invalid"),
	CASH(1, "Cash"),
	CHEQUE(2, "Cheque"),
	OTHER(3, "Other");
	
	private static Map<Integer, TransactionType> map;
	
	private final Integer value;
	private final String text;
	
	static {
		map = new HashMap<Integer, TransactionType>();
		
		for(TransactionType transactionType : values()) {
			map.put(transactionType.getValue(), transactionType);
		}
	}
	
	TransactionType(Integer value, String text) {
		this.value = value;
		this.text = text;
	}
	
	public static boolean exists(Integer value) {
		return map.containsKey(value);
	}
	
	public static TransactionType getFor(Integer value) {
		return map.get(value);
	}
	
	public int getValue() {
		return value;
	}
	
	public String getText() {
		return text;
	}
}
