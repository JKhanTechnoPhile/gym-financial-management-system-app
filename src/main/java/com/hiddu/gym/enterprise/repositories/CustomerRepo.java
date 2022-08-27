package com.hiddu.gym.enterprise.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hiddu.gym.enterprise.entities.Customer;
import com.hiddu.gym.enterprise.entities.GymBranch;
import com.hiddu.gym.enterprise.entities.GymSubscriptionPlan;

public interface CustomerRepo extends JpaRepository<Customer, Integer>{
	
	@Query("FROM Customer WHERE gymBranch.id = :gymBranchId and id = :customerId")
	Customer findByCustomerIdAndGymCode(Integer customerId, Integer gymBranchId);
	
	@Query("FROM Customer WHERE gymBranch.id = :gymBranchId and customerPhoneNumber = :contactNumber")
	Customer findByContactAndGymCode(String contactNumber, Integer gymBranchId);
	
	@Query("FROM Customer WHERE customerPhoneNumber = :contactNumber")
	List<Customer> findByContact(String contactNumber);

	List<Customer> findByGymBranch(GymBranch gymBranch);
	
	List<Customer> findByGymPlan(GymSubscriptionPlan gymPlan);
	
//	List<Customer> searchByCustomerName(String customerName);
	
	@Query("select c from Customer c where c.customerName like :key")
	List<Customer> searchByCustomerName(@Param("key") String customerName);
}
