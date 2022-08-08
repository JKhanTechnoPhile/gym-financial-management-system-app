package com.hiddu.gym.enterprise.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.hiddu.gym.enterprise.entities.Customer;
import com.hiddu.gym.enterprise.entities.GymBranch;
import com.hiddu.gym.enterprise.execptions.ResourceNotFoundException;
import com.hiddu.gym.enterprise.payloads.CustomerDto;
import com.hiddu.gym.enterprise.repositories.CustomerRepo;
import com.hiddu.gym.enterprise.repositories.GymBranchRepo;
import com.hiddu.gym.enterprise.services.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private GymBranchRepo gymBranchRepo;
	
	@Override
	public CustomerDto createCustomer(CustomerDto customerDto) {
		
		GymBranch gymBranch = gymBranchRepo.findByGymCode(customerDto.getGymBranchCode());
		if(gymBranch == null)
			throw new ResourceNotFoundException("GymBranch", "gymCode", customerDto.getGymBranchCode());
		
		Customer customer = this.dtoToCustomer(customerDto);
		customer.setGymBranch(gymBranch);
		
		switch (customer.getCustomerStatus()) {
		case CUSTOMER_ENQUIRED: {
			customer.setCustomerEnquiredDate(new Date());
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + customer.getCustomerStatus());
		}
		
		Customer savedCustomerr = this.customerRepo.save(customer);
		return this.CustomerToDto(savedCustomerr);
	}

	@Override
	public CustomerDto updateCustomer(CustomerDto customerDto, Integer customerId) {
		Customer customer = this.customerRepo.findById(customerId)
				.orElseThrow(()-> new ResourceNotFoundException("Customer","id", customerId));
		
		customer.setCustomerName(customerDto.getCustomerName());
		customer.setCustomerPhoneNumber(customerDto.getCustomerPhoneNumber());
		customer.setCustomerEnquiredDate(customerDto.getCustomerEnquiredDate());
		customer.setCustomerStatus(customerDto.getCustomerStatus());
		
		if(StringUtils.hasLength(customerDto.getCustomerEmailId())) {
			customer.setCustomerEmailId(customerDto.getCustomerEmailId());
		}
		
		if(StringUtils.hasLength(customerDto.getCustomerIdType())) {
			customer.setCustomerIdType(customerDto.getCustomerIdType());
		}
		
		if(StringUtils.hasLength(customerDto.getCustomerIdProof())) {
			customer.setCustomerIdProof(customerDto.getCustomerIdProof());
		}
		
		if(customerDto.getCustomerRegisteredDate() != null) {
			customer.setCustomerRegisteredDate(customerDto.getCustomerRegisteredDate());
		}
		
		if(customerDto.getCustomerPlanActivatedDate() != null) {
			customer.setCustomerPlanActivatedDate(customerDto.getCustomerPlanActivatedDate());
		}
		
		Customer updatedcustomer = this.customerRepo.save(customer);
		CustomerDto updatedcustomerDtoo = this.CustomerToDto(updatedcustomer);
		
		return updatedcustomerDtoo;
	}

	@Override
	public CustomerDto getCustomerById(Integer customerId) {
		Customer customer = this.customerRepo.findById(customerId)
				.orElseThrow(()-> new ResourceNotFoundException("Customer","id", customerId));
		CustomerDto customerDto = this.CustomerToDto(customer);
		return customerDto;
	}

	@Override
	public List<CustomerDto> getAllCustomers() {
		List<Customer> customers = this.customerRepo.findAll();
		List<CustomerDto> customersDtos = customers.stream().map(customer -> this.CustomerToDto(customer)).collect(Collectors.toList());
		
		return customersDtos;
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		Customer customer = this.customerRepo.findById(customerId)
				.orElseThrow(()-> new ResourceNotFoundException("Customer","id", customerId));
		this.customerRepo.delete(customer);
	}
	
	@Override
	public List<CustomerDto> geCustomersByGymBranch(Integer gymBranchId) {
		
		GymBranch gymBranch = gymBranchRepo.findById(gymBranchId)
				.orElseThrow(()-> new ResourceNotFoundException("Gym Branch","id", gymBranchId));;
		
		List<Customer> customers = this.customerRepo.findByGymBranch(gymBranch);
		List<CustomerDto> customersDtos = customers.stream().map(customer -> this.CustomerToDto(customer)).collect(Collectors.toList());
		
		return customersDtos;
	}
	
	private Customer dtoToCustomer(CustomerDto customerDto) {
		Customer customer = this.modelMapper.map(customerDto, Customer.class);
		return customer;
	}
	
	private CustomerDto CustomerToDto(Customer customer) {
		CustomerDto customerDto = this.modelMapper.map(customer, CustomerDto.class);
		return customerDto;
	}
}
