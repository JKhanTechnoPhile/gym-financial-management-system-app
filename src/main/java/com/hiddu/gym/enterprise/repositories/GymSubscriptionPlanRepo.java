package com.hiddu.gym.enterprise.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hiddu.gym.enterprise.entities.GymBranch;
import com.hiddu.gym.enterprise.entities.GymSubscriptionPlan;

public interface GymSubscriptionPlanRepo extends JpaRepository<GymSubscriptionPlan, Integer>{

	@Query("FROM GymSubscriptionPlan WHERE gymBranch.id = :gymBranchId and id = :subscriptionPlanId")
	GymSubscriptionPlan findSubscriptionPlanByGymIdAndSubscriptionId(Integer subscriptionPlanId, Integer gymBranchId);
	
	List<GymSubscriptionPlan> findByGymBranch(GymBranch gymBranch);
	
}
