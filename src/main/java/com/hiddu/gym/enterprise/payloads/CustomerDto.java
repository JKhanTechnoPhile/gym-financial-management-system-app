package com.hiddu.gym.enterprise.payloads;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.hiddu.gym.enterprise.enums.CustomerLifeCycleEnum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CustomerDto {
	
	private int id;
	
	@NotEmpty
	@Size(min = 4, message = "User must be min of 4 characters !!")
	private String customerName;
	
	@NotEmpty
	@Size(max = 15, message = "Password must be min of 3 characters and max of 15 characters !!")
	@Pattern(regexp = "^[0-9]{10}$", message = "Invalid phone number !!")
	private String customerPhoneNumber;
	
	@Email(message = "Email address is not valid !!")
	@Size(min = 0, max = 100, message = "Email address is not valid !!")
	private String customerEmailId;
	
	private String customerIdType;
	
	private String customerIdProof;
	
	private Date customerEnquiredDate;
	
	private Date customerRegisteredDate;
	
	private Date customerPlanActivatedDate;
	
	private String gymBranchCode;
	
	@NotNull
	private CustomerLifeCycleEnum customerStatus;

}
