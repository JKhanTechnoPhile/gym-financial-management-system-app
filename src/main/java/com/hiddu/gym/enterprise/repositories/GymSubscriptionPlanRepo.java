package com.hiddu.gym.enterprise.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hiddu.gym.enterprise.entities.GymBranch;
import com.hiddu.gym.enterprise.entities.GymSubscriptionPlan;

public interface GymSubscriptionPlanRepo extends JpaRepository<GymSubscriptionPlan, Integer>{

//	@Query("SELECT subscriptionPlan FROM GymSubscriptionPlan subscriptionPlan WHERE subscriptionPlan.gym_branch_id = ?1")
//	List<GymSubscriptionPlan> findSubscriptionPlansByGymCode(String gymCode);
	List<GymSubscriptionPlan> findByGymBranch(GymBranch gymBranch);
	
}
