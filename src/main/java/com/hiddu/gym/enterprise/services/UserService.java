package com.hiddu.gym.enterprise.services;

import java.util.List;

import com.hiddu.gym.enterprise.payloads.UserDto;

public interface UserService {
	
	UserDto registerNewUser(UserDto userDto);

	UserDto createUser(UserDto user);
	
	UserDto updateeUser(UserDto user, Integer userId);
	
	UserDto getUserById(Integer userId);
	
	List<UserDto> getAllUsers();
	
	void deleteUser(Integer userId);
	
	List<UserDto> getUsersByGymBranch(String gymCode);
}
