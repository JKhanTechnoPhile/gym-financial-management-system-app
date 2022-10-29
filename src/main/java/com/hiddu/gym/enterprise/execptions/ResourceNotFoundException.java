package com.hiddu.gym.enterprise.execptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {
	
	String resourceName;
	String fieldName;
	long fieldValue;
	String fieldValueAsString;
	public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
		super(String.format("%s not found with %s : %s", resourceName,fieldName,fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	
	public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
		super(String.format("%s not found with %s : %s", resourceName,fieldName,fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValueAsString = fieldValue;
	}
	
	public ResourceNotFoundException(String resourceName, String message) {
		super(String.format("%s: %s", resourceName,message));
		this.resourceName = resourceName;
		this.fieldName = message;
		this.fieldValueAsString = message;
	}
}
