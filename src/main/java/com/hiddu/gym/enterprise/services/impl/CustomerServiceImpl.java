package com.hiddu.gym.enterprise.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ValidationException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.hiddu.gym.enterprise.entities.Customer;
import com.hiddu.gym.enterprise.entities.GymBranch;
import com.hiddu.gym.enterprise.entities.GymSubscriptionPlan;
import com.hiddu.gym.enterprise.enums.CustomerLifeCycleEnum;
import com.hiddu.gym.enterprise.execptions.ResourceNotFoundException;
import com.hiddu.gym.enterprise.payloads.CustomerDto;
import com.hiddu.gym.enterprise.payloads.CustomerResponse;
import com.hiddu.gym.enterprise.repositories.CustomerRepo;
import com.hiddu.gym.enterprise.repositories.GymBranchRepo;
import com.hiddu.gym.enterprise.repositories.GymSubscriptionPlanRepo;
import com.hiddu.gym.enterprise.services.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private GymBranchRepo gymBranchRepo;
	
	@Autowired
	private GymSubscriptionPlanRepo gymSubscriptionPlanRepo;
	
	@Override
	public CustomerDto createCustomer(CustomerDto customerDto) {
		
		Customer customer = this.dtoToCustomer(customerDto);
		
		if(customer.getCustomerStatus() != CustomerLifeCycleEnum.CUSTOMER_ENQUIRED) {
			GymBranch gymBranch = gymBranchRepo.findByGymCode(customerDto.getGymBranchCode());
			if(gymBranch == null)
				throw new ResourceNotFoundException("GymBranch", "gymCode", customerDto.getGymBranchCode());
			customer.setGymBranch(gymBranch);
		}
		
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
	public CustomerDto updateCustomer(CustomerDto customerDto, String gymCode, String contactNumber) {
		
		GymBranch gymBranch = gymBranchRepo.findByGymCode(gymCode);
		if(gymBranch == null)
			throw new ResourceNotFoundException("GymBranch", "gymCode", gymCode);
		
		
		Customer customer = this.customerRepo.findByContactAndGymCode(contactNumber, gymBranch.getId());		
		if(customer == null)
			throw new ResourceNotFoundException("Customer", "contactNumber", contactNumber);
		
		customer.setGymBranch(gymBranch);
		customer.setCustomerName(customerDto.getCustomerName());
		customer.setCustomerPhoneNumber(customerDto.getCustomerPhoneNumber());
		customer.setCustomerEnquiredDate(new Date(customerDto.getCustomerEnquiredDate()));
		customer.setCustomerStatus(customerDto.getCustomerStatus());
		
		if(StringUtils.hasLength(customerDto.getCustomerIdProof())) {
			customer.setCustomerIdProof(customerDto.getCustomerIdProof());
		}
		if(StringUtils.hasLength(customerDto.getCustomerIdType())) {
			customer.setCustomerIdType(customerDto.getCustomerIdType());
		}
		
		if(StringUtils.hasLength(customerDto.getCustomerEmailId())) {
			customer.setCustomerEmailId(customerDto.getCustomerEmailId());
		}
		
		switch (customer.getCustomerStatus()) {
		case CUSTOMER_ENQUIRED: {
			if(customer.getCustomerEnquiredDate() == null) {
				customer.setCustomerEnquiredDate(new Date());
			}
			break;
		}
		case CUSTOMER_REGISTERED: {
			
			if(!StringUtils.hasLength(customerDto.getCustomerIdType()) && !StringUtils.hasLength(customerDto.getCustomerIdProof())) {
				throw new ValidationException("Customer Id Proof could not be empty - Customer Id / Proof");
			}
			
			customer.setCustomerIdType(customerDto.getCustomerIdType());			
			customer.setCustomerIdProof(customerDto.getCustomerIdProof());
			customer.setCustomerRegisteredDate(new Date());
			break;
		}
		case CUSTOMER_PLAN_ACTIVE: {
			GymSubscriptionPlan gymSubscriptionPlan = gymSubscriptionPlanRepo.findById(customerDto.getGymSubscriptionPlanCode())
					.orElseThrow(()-> new ResourceNotFoundException("GymSubscriptionPlan","id", customerDto.getGymSubscriptionPlanCode()));
			
			customer.setCustomerPlanActivatedDate(new Date());
			customer.setGymPlan(gymSubscriptionPlan);
			
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + customer.getCustomerStatus());
		}
		
//		if(customerDto.getCustomerRegisteredDate() != null) {
//			customer.setCustomerRegisteredDate(customerDto.getCustomerRegisteredDate());
//		}
//		
//		if(customerDto.getCustomerPlanActivatedDate() != null) {
//			customer.setCustomerPlanActivatedDate(customerDto.getCustomerPlanActivatedDate());
//		}
		
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
	public CustomerResponse getAllCustomersByPagination(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		
//		Pageable p = PageRequest.of(pageNumber, pageSize);
		
//		Pageable p = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
//		Sort sort = null;
//		if(sortDir.equalsIgnoreCase("asc")) {
//			sort = Sort.by(sortBy).ascending();
//		} else {
//			sort = Sort.by(sortBy).descending();
//		}
		Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		Page<Customer> pagedCustomers = this.customerRepo.findAll(p);
		
		List<Customer> customers = pagedCustomers.getContent();
		
		List<CustomerDto> customersDtos = customers.stream().map(customer -> this.CustomerToDto(customer)).collect(Collectors.toList());
		
		CustomerResponse customerResponse = new CustomerResponse();
		customerResponse.setContent(customersDtos);
		customerResponse.setPageNumber(pagedCustomers.getNumber());
		customerResponse.setPageSize(pagedCustomers.getSize());
		customerResponse.setTotalElements(pagedCustomers.getTotalElements());
		customerResponse.setTotalPages(pagedCustomers.getTotalPages());
		customerResponse.setLastPage(pagedCustomers.isLast());
		
		
		return customerResponse;
	}
	

	@Override
	public void deleteCustomer(String gymCode, String contactNumber) {
		
		GymBranch gymBranch = gymBranchRepo.findByGymCode(gymCode);
		if(gymBranch == null)
			throw new ResourceNotFoundException("GymBranch", "gymCode", gymCode);
		
		
		Customer customer = this.customerRepo.findByContactAndGymCode(contactNumber, gymBranch.getId());		
		if(customer == null)
			throw new ResourceNotFoundException("Customer", "contactNumber", contactNumber);
		
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
	
	@Override
	public List<CustomerDto> searchCustomers(String keyword) {
		List<Customer> customers = this.customerRepo.searchByCustomerName(keyword);
		List<CustomerDto> customersDto = customers.stream().map(customer -> this.CustomerToDto(customer)).collect(Collectors.toList());
		return customersDto;
	}
	
	private Customer dtoToCustomer(CustomerDto customerDto) {
		Customer customer = this.modelMapper.map(customerDto, Customer.class);
		return customer;
	}
	
	private CustomerDto CustomerToDto(Customer customer) {
		CustomerDto customerDto = this.modelMapper.map(customer, CustomerDto.class);
		return customerDto;
	}

	@Override
	public CustomerDto getCustomerByIdAndGymBranchId(Integer customerId, String gymCode) {
		
		GymBranch gymBranch = gymBranchRepo.findByGymCode(gymCode);
		if(gymBranch == null)
			throw new ResourceNotFoundException("Gym Branch","gymCode", gymCode);
		
		Customer customer = this.customerRepo.findByCustomerIdAndGymCode(customerId, gymBranch.getId());
		if(customer == null)
			throw new ResourceNotFoundException("Customer","id", customerId);
		
		return this.modelMapper.map(customer, CustomerDto.class);
	}

	@Override
	public CustomerDto getCustomerByContactAndGymBranchId(String contactNumber, String gymCode) {
		
		GymBranch gymBranch = gymBranchRepo.findByGymCode(gymCode);
		if(gymBranch == null)
			throw new ResourceNotFoundException("Gym Branch","gymCode", gymCode);
		
		Customer customer = this.customerRepo.findByContactAndGymCode(contactNumber, gymBranch.getId());
		if(customer == null)
			throw new ResourceNotFoundException("Customer","contactNumber", contactNumber);
		
		return this.modelMapper.map(customer, CustomerDto.class);
	}

	@Override
	public List<CustomerDto> geCustomersByContactNumber(String contactNumber) {
		List<Customer> customers = this.customerRepo.findByContact(contactNumber);
		List<CustomerDto> customersDtos = customers.stream().map(customer -> this.CustomerToDto(customer)).collect(Collectors.toList());
		return customersDtos;
	}
}
