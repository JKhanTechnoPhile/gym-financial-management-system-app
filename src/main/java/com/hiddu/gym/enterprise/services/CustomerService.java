package com.hiddu.gym.enterprise.services;

import java.util.List;

import com.hiddu.gym.enterprise.payloads.CustomerDto;
import com.hiddu.gym.enterprise.payloads.CustomerResponse;

public interface CustomerService {

	CustomerDto createCustomer(CustomerDto customer);
	
	CustomerDto updateCustomer(CustomerDto customer, String gymCode, String contactNumber);
	
	CustomerDto getCustomerById(Integer customerId);
	
	CustomerDto getCustomerByIdAndGymBranchId(Integer customerId, String GymBranchCode);
	
	CustomerDto getCustomerByContactAndGymBranchId(String contactNumber, String GymBranchCode);
	
	List<CustomerDto> getAllCustomers();
	
	CustomerResponse getAllCustomersByPagination(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	void deleteCustomer(String gymCode, String contactNumber);
	
	List<CustomerDto> geCustomersByGymBranch(Integer gymBranchId);
	
	List<CustomerDto> geCustomersByContactNumber(String contactNumber);
	
	List<CustomerDto> searchCustomers(String keyword);
}
