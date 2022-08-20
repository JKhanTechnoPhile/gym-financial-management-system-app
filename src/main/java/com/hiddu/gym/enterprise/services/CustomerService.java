package com.hiddu.gym.enterprise.services;

import java.util.List;

import com.hiddu.gym.enterprise.payloads.CustomerDto;
import com.hiddu.gym.enterprise.payloads.CustomerResponse;

public interface CustomerService {

	CustomerDto createCustomer(CustomerDto customer);
	
	CustomerDto updateCustomer(CustomerDto customer, Integer customerId);
	
	CustomerDto getCustomerById(Integer customerId);
	
	List<CustomerDto> getAllCustomers();
	
	CustomerResponse getAllCustomersByPagination(Integer pageNumber, Integer pageSize);
	
	void deleteCustomer(Integer customerId);
	
	List<CustomerDto> geCustomersByGymBranch(Integer gymBranchId);
}
