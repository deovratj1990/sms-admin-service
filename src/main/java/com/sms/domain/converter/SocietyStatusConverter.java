package com.sms.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.sms.domain.constant.SocietyStatus;

@Converter
public class SocietyStatusConverter implements AttributeConverter<SocietyStatus, Integer> {

	@Override
	public Integer convertToDatabaseColumn(SocietyStatus attribute) {
		return attribute.toInteger();
	}

	@Override
	public SocietyStatus convertToEntityAttribute(Integer column) {
		return SocietyStatus.parseEnum(column);
	}

}
