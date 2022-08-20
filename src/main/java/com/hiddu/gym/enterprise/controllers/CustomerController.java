package com.hiddu.gym.enterprise.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hiddu.gym.enterprise.config.AppConstants;
import com.hiddu.gym.enterprise.payloads.ApiResponse;
import com.hiddu.gym.enterprise.payloads.CustomerDto;
import com.hiddu.gym.enterprise.payloads.CustomerResponse;
import com.hiddu.gym.enterprise.services.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	//POST-create customer
	@PostMapping("/")
	public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CustomerDto customerDto) {
		CustomerDto createdCustomerDto = this.customerService.createCustomer(customerDto);
		return new ResponseEntity<>(createdCustomerDto, HttpStatus.CREATED);
	}
	
	//PUT-update customer
	@PutMapping("/{customerId}")
	public ResponseEntity<CustomerDto> updateCustomer(@Valid @RequestBody CustomerDto customerDto, @PathVariable("customerId") Integer cId) {
		CustomerDto updatedUser = this.customerService.updateCustomer(customerDto, cId);
		return ResponseEntity.ok(updatedUser);
	}
	
	//DELETE-delete customer
	@DeleteMapping("/{customerId}")
	public ResponseEntity<ApiResponse> deleteCustomer(@PathVariable Integer customerId) {
		this.customerService.deleteCustomer(customerId);
		return ResponseEntity.ok(new ApiResponse("Customer deleted successfully", true));
	}
	
	//GET-customer get
	@GetMapping("/")
	public ResponseEntity<List<CustomerDto>> getAllCustomers() {
		return ResponseEntity.ok(this.customerService.getAllCustomers());
	}
	
	//GET-customer by pagination (PageNumber starts with zero)
	@GetMapping("/pagination")
	public ResponseEntity<CustomerResponse> getAllCustomersByPagination(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = "customerName", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
			) {
		return ResponseEntity.ok(this.customerService.getAllCustomersByPagination(pageNumber, pageSize, sortBy, sortDir));
	}
	
	@GetMapping("/{customerId}")
	public ResponseEntity<CustomerDto> getCustomer(@PathVariable Integer customerId) {
		return ResponseEntity.ok(this.customerService.getCustomerById(customerId));
	}
	
	//Search
	@GetMapping("/search/{keywords}")
	public ResponseEntity<List<CustomerDto>> getAllCustomersBySearch(
			@PathVariable("keywords") String keywords
			) {
		return ResponseEntity.ok(this.customerService.searchCustomers(keywords));
		
	}
	
}
