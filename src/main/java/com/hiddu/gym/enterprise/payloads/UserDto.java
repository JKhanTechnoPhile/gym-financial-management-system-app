package com.hiddu.gym.enterprise.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


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
	@Pattern(regexp = "^[0-9]{10}$", message = "Invalid phone number !!")
	private String phoneNumber;
	
	@Email(message = "Email address is not valid !!")
	private String emailId;
	
//	@NotEmpty
//	@Size(min = 3, max = 15, message = "Password must be min of 3 characters and max of 15 characters !!")
//	@Pattern(regexp = "^[a-zA-Z0-9]{10}$")
	private String password;
	
	private String userIdType;
	
	private String userIdProof;
	
	private Date userCreatedDate;
	
	@NotEmpty
	private String userType;
	
	@NotEmpty
	private int roleType;
	
	@NotEmpty
	private String gymBranchCode;
	
	private GymBranchDto gymBranch;
	
	private Set<RoleDto> roles = new HashSet<>();
}
