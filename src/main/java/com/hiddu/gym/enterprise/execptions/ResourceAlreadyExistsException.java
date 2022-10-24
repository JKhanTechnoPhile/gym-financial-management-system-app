package com.hiddu.gym.enterprise.execptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceAlreadyExistsException extends RuntimeException {
	
	String resourceName;
	String fieldName;
	long fieldValue;
	String fieldValueAsString;
	public ResourceAlreadyExistsException(String resourceName, String fieldName, long fieldValue) {
		super(String.format("%s already exists with %s : %s", resourceName,fieldName,fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	
	public ResourceAlreadyExistsException(String resourceName, String fieldName, String fieldValue) {
		super(String.format("%s already exists with %s : %s", resourceName,fieldName,fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValueAsString = fieldValue;
	}
}
