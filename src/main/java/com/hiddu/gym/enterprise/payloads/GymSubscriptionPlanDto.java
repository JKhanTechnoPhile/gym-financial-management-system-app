package com.hiddu.gym.enterprise.payloads;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GymSubscriptionPlanDto {
	
	private Integer gymSubscriptionPlanId;
	
	@NotEmpty
	@Size(min = 4, message = "Gym subscription plan name must be min of 4 characters !!")
	private String gymPlanName;
	
	@NotEmpty
	private String gymPlanFrequency;
	
	@NotEmpty
	private String gymPlanBaseFare;
	
	private Date gymPlanCreatedDate;
	
	private Date gymPlanEndDate;
	
	private Date gymPlanEditedDate;
	
	@NotEmpty
	private String gymBranchCode;
	
	private GymBranchDto gymBranch;

}
