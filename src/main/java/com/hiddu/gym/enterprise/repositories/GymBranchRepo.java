package com.hiddu.gym.enterprise.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hiddu.gym.enterprise.entities.GymBranch;

public interface GymBranchRepo extends JpaRepository<GymBranch, Integer>{

	@Query("SELECT branch FROM GymBranch branch WHERE branch.gymCode = ?1")
	GymBranch findByGymCode(String gymCode);
	
}
