package com.hiddu.gym.enterprise.enums.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.hiddu.gym.enterprise.enums.UserIdTypeEnum;

@Converter
public class UserIdTypeEnumJpaConverter implements AttributeConverter<UserIdTypeEnum, String> {

	@Override
	public String convertToDatabaseColumn(UserIdTypeEnum attribute) {
		if (attribute != null)
			return attribute.toString();
		else
			return null;
	}

	@Override
	public UserIdTypeEnum convertToEntityAttribute(String dbData) {
		if (dbData == null) {
			return null;
		}
		try {
			return UserIdTypeEnum.valueOf(dbData);
		}catch (IllegalArgumentException e) {
			return null;
		}
	}

}
