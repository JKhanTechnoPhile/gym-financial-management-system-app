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
import org.springframework.web.bind.annotation.RestController;

import com.hiddu.gym.enterprise.payloads.ApiResponse;
import com.hiddu.gym.enterprise.payloads.CustomerDto;
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
	
	@GetMapping("/{customerId}")
	public ResponseEntity<CustomerDto> getCustomer(@PathVariable Integer userId) {
		return ResponseEntity.ok(this.customerService.getCustomerById(userId));
	}
	
}