package com.hiddu.gym.enterprise.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hiddu.gym.enterprise.config.AppConstants;
import com.hiddu.gym.enterprise.payloads.ApiResponse;
import com.hiddu.gym.enterprise.payloads.CustomerDto;
import com.hiddu.gym.enterprise.payloads.CustomerResponse;
import com.hiddu.gym.enterprise.services.CustomerService;
import com.hiddu.gym.enterprise.services.FileService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	//POST-create customer
	@PreAuthorize("hasAnyRole('PLATFORM_ADMIN','BRANCH_ADMIN', 'BRANCH_MANAGER')")
	@PostMapping("/")
	public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CustomerDto customerDto) {
		CustomerDto createdCustomerDto = this.customerService.createCustomer(customerDto);
		return new ResponseEntity<>(createdCustomerDto, HttpStatus.CREATED);
	}
	
	//PUT-update customer
	@PreAuthorize("hasAnyRole('PLATFORM_ADMIN','BRANCH_ADMIN', 'BRANCH_MANAGER')")
	@PutMapping("/{gymCode}/{contactNumber}")
	public ResponseEntity<CustomerDto> updateCustomer(@Valid @RequestBody CustomerDto customerDto, @PathVariable String gymCode, @PathVariable("contactNumber") String contactNumber) {
		CustomerDto updatedUser = this.customerService.updateCustomer(customerDto, gymCode, contactNumber);
		return ResponseEntity.ok(updatedUser);
	}
	
	//PUT-update customer to register before activate plan
	@PreAuthorize("hasAnyRole('PLATFORM_ADMIN','BRANCH_ADMIN', 'BRANCH_MANAGER')")
	@PutMapping("/{customerId}")
	public ResponseEntity<CustomerDto> updateCustomerToRegister(@Valid @RequestBody CustomerDto customerDto, @PathVariable("customerId") Integer customerId) {
		CustomerDto updatedUser = this.customerService.updateCustomerToRegister(customerDto, customerId);
		return ResponseEntity.ok(updatedUser);
	}
	
	//DELETE-delete customer
	@PreAuthorize("hasAnyRole('PLATFORM_ADMIN','BRANCH_ADMIN', 'BRANCH_MANAGER')")
	@DeleteMapping("/{gymCode}/{contactNumber}")
	public ResponseEntity<ApiResponse> deleteCustomer(@PathVariable String gymCode, @PathVariable("contactNumber") String contactNumber) {
		this.customerService.deleteCustomer(gymCode, contactNumber);
		return ResponseEntity.ok(new ApiResponse("Customer deleted successfully", true));
	}
	
	//GET-customer get
	@GetMapping("/")
	public ResponseEntity<List<CustomerDto>> getAllCustomers() {
		return ResponseEntity.ok(this.customerService.getAllCustomers());
	}
	
	//GET-customer get
	@GetMapping("/gym/{gymCode}")
	public ResponseEntity<List<CustomerDto>> getAllCustomersByGymBranchCode(@PathVariable String gymCode) {
		return ResponseEntity.ok(this.customerService.geCustomersByGymBranchCode(gymCode));
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
	public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Integer customerId) {
		return ResponseEntity.ok(this.customerService.getCustomerById(customerId));
	}
	
	@GetMapping("/gym/{gymCode}/{customerId}")
	public ResponseEntity<CustomerDto> getCustomerByGymCodeAndId(@PathVariable String gymCode, @PathVariable Integer customerId) {
		return ResponseEntity.ok(this.customerService.getCustomerByIdAndGymBranchId(customerId, gymCode));
	}
	
	@GetMapping("/gym/{gymCode}/contact/{contactNumber}")
	public ResponseEntity<CustomerDto> getCustomerByGymCodeAndContact(@PathVariable String gymCode, @PathVariable String contactNumber) {
		return ResponseEntity.ok(this.customerService.getCustomerByContactAndGymBranchId(contactNumber, gymCode));
	}
	
	//GET-customer get
	@GetMapping("/contact/{contactNumber}")
	public ResponseEntity<List<CustomerDto>> getAllCustomersByContact(@PathVariable String contactNumber) {
		return ResponseEntity.ok(this.customerService.geCustomersByContactNumber(contactNumber));
	}
	
	//Search
	@GetMapping("/search/{keywords}")
	public ResponseEntity<List<CustomerDto>> getAllCustomersBySearch(
			@PathVariable("keywords") String keywords
			) {
		return ResponseEntity.ok(this.customerService.searchCustomers(keywords));
		
	}
	
	//Image upload
	@PreAuthorize("hasAnyRole('PLATFORM_ADMIN','BRANCH_ADMIN', 'BRANCH_MANAGER')")
	@PostMapping("/upload/image/{gymCode}/{contactNumber}")
	public ResponseEntity<CustomerDto> uploadCustomerDocumentAny(
			@RequestParam("image") MultipartFile image,
			@PathVariable String gymCode, @PathVariable String contactNumber
			) throws IOException {
		CustomerDto customerDto = this.customerService.getCustomerByContactAndGymBranchId(contactNumber, gymCode);
		
		String fileName = this.fileService.uploadImage(path, image);
		
		customerDto.setCustomerIdProof(fileName);
		customerDto.setCustomerIdType("Aadhaar");
		
		CustomerDto updatedCustomerDto = this.customerService.updateCustomer(customerDto, gymCode, contactNumber);
		
		
		return ResponseEntity.ok(updatedCustomerDto);
	}
	
	//Download Image
	@GetMapping(value = "/download/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable("imageName") String imageName,
			HttpServletResponse response
			) throws IOException {
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
}
