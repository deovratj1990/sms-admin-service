package com.sms.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.sms.domain.constant.TransactionType;

@Converter
public class TransactionTypeConverter implements AttributeConverter<TransactionType, Integer> {

	@Override
	public Integer convertToDatabaseColumn(TransactionType attribute) {
		return attribute.toInteger();
	}

	@Override
	public TransactionType convertToEntityAttribute(Integer column) {
		return TransactionType.parseEnum(column);
	}

}
