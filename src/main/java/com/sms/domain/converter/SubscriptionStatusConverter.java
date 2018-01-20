package com.sms.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.sms.domain.constant.SubscriptionStatus;

@Converter
public class SubscriptionStatusConverter implements AttributeConverter<SubscriptionStatus, Integer> {

	@Override
	public Integer convertToDatabaseColumn(SubscriptionStatus attribute) {
		return attribute.toInteger();
	}

	@Override
	public SubscriptionStatus convertToEntityAttribute(Integer column) {
		return SubscriptionStatus.parseEnum(column);
	}

}
