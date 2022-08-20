package com.hiddu.gym.enterprise.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hiddu.gym.enterprise.entities.Customer;
import com.hiddu.gym.enterprise.entities.GymBranch;
import com.hiddu.gym.enterprise.entities.GymSubscriptionPlan;

public interface CustomerRepo extends JpaRepository<Customer, Integer>{

	List<Customer> findByGymBranch(GymBranch gymBranch);
	
	List<Customer> findByGymPlan(GymSubscriptionPlan gymPlan);
	
//	List<Customer> searchByCustomerName(String customerName);
	
	@Query("select c from Customer c where c.customerName like :key")
	List<Customer> searchByCustomerName(@Param("key") String customerName);
}
