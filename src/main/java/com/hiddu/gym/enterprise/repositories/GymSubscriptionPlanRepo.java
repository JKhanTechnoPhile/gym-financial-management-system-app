package com.hiddu.gym.enterprise.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hiddu.gym.enterprise.entities.GymBranch;
import com.hiddu.gym.enterprise.entities.GymSubscriptionPlan;
import com.hiddu.gym.enterprise.enums.GymPlanFrequencyEnum;

public interface GymSubscriptionPlanRepo extends JpaRepository<GymSubscriptionPlan, Integer>{

	@Query("FROM GymSubscriptionPlan WHERE gymBranch.id = :gymBranchId and id = :subscriptionPlanId")
	GymSubscriptionPlan findSubscriptionPlanByGymIdAndSubscriptionId(Integer subscriptionPlanId, Integer gymBranchId);
	
	@Query("FROM GymSubscriptionPlan WHERE gymBranch.id = :gymBranchId and gymPlanFrequency = :gymPlanFrequency and gymPlanBaseFare = :gymPlanBaseFare")
	GymSubscriptionPlan findSubscriptionPlanBySearch(GymPlanFrequencyEnum gymPlanFrequency, Float gymPlanBaseFare, Integer gymBranchId);
	
	List<GymSubscriptionPlan> findByGymBranch(GymBranch gymBranch);
	
}
