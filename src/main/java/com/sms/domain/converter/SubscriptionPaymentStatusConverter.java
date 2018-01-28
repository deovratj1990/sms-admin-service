package com.sms.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.sms.domain.constant.SubscriptionPaymentStatus;

@Converter
public class SubscriptionPaymentStatusConverter implements AttributeConverter<SubscriptionPaymentStatus, Integer> {

	@Override
	public Integer convertToDatabaseColumn(SubscriptionPaymentStatus attribute) {
		return attribute.toInteger();
	}

	@Override
	public SubscriptionPaymentStatus convertToEntityAttribute(Integer column) {
		return SubscriptionPaymentStatus.parseEnum(column);
	}

}
