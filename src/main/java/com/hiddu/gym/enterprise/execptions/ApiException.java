package com.hiddu.gym.enterprise.execptions;

public class ApiException extends RuntimeException {
	
	public ApiException(String message) {
		super(message);
	}
	
	public ApiException() {
		super();
	}

}
