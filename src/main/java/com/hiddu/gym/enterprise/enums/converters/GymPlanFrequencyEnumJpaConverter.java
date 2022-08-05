package com.hiddu.gym.enterprise.enums.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.hiddu.gym.enterprise.enums.GymPlanFrequencyEnum;

@Converter
public class GymPlanFrequencyEnumJpaConverter implements AttributeConverter<GymPlanFrequencyEnum, String> {

	@Override
	public String convertToDatabaseColumn(GymPlanFrequencyEnum attribute) {
		return attribute.toString();
	}

	@Override
	public GymPlanFrequencyEnum convertToEntityAttribute(String dbData) {
		if (dbData == null) {
			return null;
		}
		try {
			return GymPlanFrequencyEnum.valueOf(dbData);
		}catch (IllegalArgumentException e) {
			return null;
		}
	}

}
