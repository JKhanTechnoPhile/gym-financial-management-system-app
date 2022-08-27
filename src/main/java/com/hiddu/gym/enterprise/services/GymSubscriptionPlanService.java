package com.hiddu.gym.enterprise.services;

import java.util.List;

import com.hiddu.gym.enterprise.payloads.GymSubscriptionPlanDto;

public interface GymSubscriptionPlanService {

	GymSubscriptionPlanDto createGymSubscriptionPlan(GymSubscriptionPlanDto gymBranch);
	
	GymSubscriptionPlanDto updateGymSubscriptionPlan(GymSubscriptionPlanDto gymBranch, String gymCode, Integer gymId);
	
	GymSubscriptionPlanDto getGymSubscriptionPlanById(String gymCode, Integer gymId);
	
	List<GymSubscriptionPlanDto> getAllGymSubscriptionPlans();
	
	void deleteGymSubscriptionPlan(String gymCode, Integer gymId);
	
	List<GymSubscriptionPlanDto> getGymSubscriptionPlansByBranch(String gymCode);
}
