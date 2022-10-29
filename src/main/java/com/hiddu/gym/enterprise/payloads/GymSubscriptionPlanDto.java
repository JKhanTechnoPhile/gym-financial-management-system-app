package com.hiddu.gym.enterprise.payloads;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GymSubscriptionPlanDto {
	
	private Integer id;
	
	@NotEmpty
	@Size(min = 4, message = "Gym subscription plan name must be min of 4 characters !!")
	private String gymPlanName;
	
	@NotEmpty
	@Size(min = 4, message = "Gym subscription frequency need to select !!")
	private String gymPlanFrequency;
	
	@NotNull
	private Float gymPlanBaseFare;
	
	private Long gymPlanCreatedDate;
	
	private Long gymPlanEndDate;
	
	private Long gymPlanEditedDate;
	
	@NotEmpty
	private String gymBranchCode;
	
	private GymBranchDto gymBranch;

}
