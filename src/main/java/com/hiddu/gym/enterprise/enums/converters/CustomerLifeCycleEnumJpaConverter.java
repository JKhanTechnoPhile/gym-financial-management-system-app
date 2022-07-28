package com.hiddu.gym.enterprise.enums.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.hiddu.gym.enterprise.enums.CustomerLifeCycleEnum;

@Converter
public class CustomerLifeCycleEnumJpaConverter implements AttributeConverter<CustomerLifeCycleEnum, String> {

	@Override
	public String convertToDatabaseColumn(CustomerLifeCycleEnum attribute) {
		return attribute.toString();
	}

	@Override
	public CustomerLifeCycleEnum convertToEntityAttribute(String dbData) {
		if (dbData == null) {
			return null;
		}
		try {
			return CustomerLifeCycleEnum.valueOf(dbData);
		}catch (IllegalArgumentException e) {
			return null;
		}
	}

}
