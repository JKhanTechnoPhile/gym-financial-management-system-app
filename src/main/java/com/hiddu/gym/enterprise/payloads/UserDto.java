package com.hiddu.gym.enterprise.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	private int id;
	private String userName;
	private String phoneNumber;
	private String emailId;
	private String password;

}
