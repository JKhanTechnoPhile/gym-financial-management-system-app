package com.hiddu.gym.enterprise.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hiddu.gym.enterprise.payloads.ApiResponse;
import com.hiddu.gym.enterprise.payloads.UserDto;
import com.hiddu.gym.enterprise.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	//POST-create user
	@PreAuthorize("hasAnyRole('PLATFORM_ADMIN','BRANCH_ADMIN')")
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		UserDto createdUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
	}
	
	//PUT-update user
	@PreAuthorize("hasAnyRole('PLATFORM_ADMIN','BRANCH_ADMIN')")
	@PutMapping("/{userId}")
//	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable Integer userId) {
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer uId) {
		UserDto updatedUser = this.userService.updateeUser(userDto, uId);
//		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
		return ResponseEntity.ok(updatedUser);
	}
	
	//DELETE-delete user
//	@PreAuthorize("hasRole('PLATFORM_ADMIN')")	//support single role validation
	@PreAuthorize("hasAnyRole('PLATFORM_ADMIN','BRANCH_ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId) {
		this.userService.deleteUser(userId);
//		return new ResponseEntity(Map.of("Message", "User Deleted Successfully"), HttpStatus.OK);
		return ResponseEntity.ok(new ApiResponse("User deleted successfully", true));
	}
	
	//GET-user get
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		return ResponseEntity.ok(this.userService.getAllUsers());
	}
	
//	@GetMapping("/{userId}")
//	public ResponseEntity<UserDto> getUser(@PathVariable Integer userId) {
//		return ResponseEntity.ok(this.userService.getUserById(userId));
//	}
	
	@GetMapping("/{gymCode}")
	public ResponseEntity<List<UserDto>> getAllUsersByGymCode(@PathVariable("gymCode") String gymCode) {
		return ResponseEntity.ok(this.userService.getUsersByGymBranch(gymCode));
	}
	
}
