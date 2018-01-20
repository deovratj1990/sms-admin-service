package com.sms.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.sms.domain.constant.SubscriptionType;

@Converter
public class SubscriptionTypeConverter implements AttributeConverter<SubscriptionType, Integer> {

	@Override
	public Integer convertToDatabaseColumn(SubscriptionType attribute) {
		return attribute.toInteger();
	}

	@Override
	public SubscriptionType convertToEntityAttribute(Integer column) {
		return SubscriptionType.parseEnum(column);
	}

}
