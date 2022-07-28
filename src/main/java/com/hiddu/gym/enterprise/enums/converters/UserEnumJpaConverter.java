package com.hiddu.gym.enterprise.enums.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.hiddu.gym.enterprise.enums.UserEnum;

@Converter
public class UserEnumJpaConverter implements AttributeConverter<UserEnum, String> {

	@Override
	public String convertToDatabaseColumn(UserEnum attribute) {
		return attribute.toString();
	}

	@Override
	public UserEnum convertToEntityAttribute(String dbData) {
		if (dbData == null) {
			return null;
		}
		try {
			return UserEnum.valueOf(dbData);
		}catch (IllegalArgumentException e) {
			return null;
		}
	}

}
