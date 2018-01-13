package com.sms.domain.constant;

import java.util.HashMap;
import java.util.Map;

public enum TransactionType {
	CASH(1, "Cash"),
	CHEQUE(2, "Cheque"),
	OTHER(3, "Other");
	
	private static Map<Integer, TransactionType> map;
	
	private final Integer value;
	private final String text;
	
	static {
		map = new HashMap<Integer, TransactionType>();
		
		for(TransactionType transactionType : values()) {
			map.put(transactionType.toInteger(), transactionType);
		}
	}
	
	TransactionType(Integer value, String text) {
		this.value = value;
		this.text = text;
	}
	
	public static TransactionType parseEnum(Integer value) {
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
