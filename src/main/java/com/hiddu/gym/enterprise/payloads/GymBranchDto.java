package com.hiddu.gym.enterprise.payloads;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.hiddu.gym.enterprise.entities.Customer;

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
	private Float gymLocationLat;
	
	@NotEmpty
	private Float gymLocationLong;
	
	@NotEmpty
	private String gymFullAddress;
	
	@NotEmpty
	private String gymContact;
	
	@NotEmpty
	private String gymCode;
	
	private List<Customer> customers = new ArrayList<>();
}
