package com.hiddu.gym.enterprise.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hiddu.gym.enterprise.entities.Customer;
import com.hiddu.gym.enterprise.entities.GymBranch;
import com.hiddu.gym.enterprise.entities.GymPlan;

public interface CustomerRepo extends JpaRepository<Customer, Integer>{

	List<Customer> findByGymBranch(GymBranch gymBranch);
	
	List<Customer> findByGymPlan(GymPlan gymPlan);
}
