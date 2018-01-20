package com.sms.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.sms.domain.constant.TransactionStatus;

@Converter
public class TransactionStatusConverter implements AttributeConverter<TransactionStatus, Integer> {

	@Override
	public Integer convertToDatabaseColumn(TransactionStatus attribute) {
		return attribute.toInteger();
	}

	@Override
	public TransactionStatus convertToEntityAttribute(Integer column) {
		return TransactionStatus.parseEnum(column);
	}

}
