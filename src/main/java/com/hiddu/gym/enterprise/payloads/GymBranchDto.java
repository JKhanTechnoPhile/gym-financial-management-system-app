package com.hiddu.gym.enterprise.payloads;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.hiddu.gym.enterprise.entities.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GymBranchDto {
	
	private Integer gymBranchId;
	
	@NotEmpty
	@Size(min = 4, message = "Gym name must be min of 4 characters !!")
	private String gymName;
	
	@NotEmpty
	private String gymLocationLat;
	
	@NotEmpty
	private String gymLocationLong;
	
	@NotEmpty
	private String gymFullAddress;
	
	@NotEmpty
	private String gymContact;
	
	private String gymCode;
}
