package com.hiddu.gym.enterprise.payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.hiddu.gym.enterprise.enums.UserEnum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	private int id;
	@NotEmpty
	@Size(min = 4, message = "User must be min of 4 characters !!")
	private String userName;
	@NotEmpty
	@Size(max = 15, message = "Password must be min of 3 characters and max of 15 characters !!")
	@Pattern(regexp = "^+(91)[0-9]{0,14}$", message = "Invalid phone number !!")
	private String phoneNumber;
	@Email(message = "Email address is not valid !!")
	private String emailId;
	@NotEmpty
	@Size(min = 3, max = 15, message = "Password must be min of 3 characters and max of 15 characters !!")
	@Pattern(regexp = "^[a-zA-Z0-9]{10}$")
	private String password;
	@NotNull
	private UserEnum userType;

}
