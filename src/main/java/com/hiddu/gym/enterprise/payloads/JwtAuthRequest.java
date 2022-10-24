package com.hiddu.gym.enterprise.payloads;

import lombok.Data;

@Data
public class JwtAuthRequest {
	
	private String username;
	
	private String password;
	
	private int roleType;
	
	public String getUserForAuthentication() {
		return username+String.valueOf(Character.LINE_SEPARATOR)+String.valueOf(roleType);
	}

}
