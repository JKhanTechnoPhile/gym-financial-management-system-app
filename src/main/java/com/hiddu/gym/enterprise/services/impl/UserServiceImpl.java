package com.hiddu.gym.enterprise.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hiddu.gym.enterprise.config.AppConstants;
import com.hiddu.gym.enterprise.entities.GymBranch;
import com.hiddu.gym.enterprise.entities.Role;
import com.hiddu.gym.enterprise.entities.User;
import com.hiddu.gym.enterprise.enums.UserEnum;
import com.hiddu.gym.enterprise.execptions.ResourceAlreadyExistsException;
import com.hiddu.gym.enterprise.execptions.ResourceNotFoundException;
import com.hiddu.gym.enterprise.payloads.UserDto;
import com.hiddu.gym.enterprise.repositories.GymBranchRepo;
import com.hiddu.gym.enterprise.repositories.RoleRepo;
import com.hiddu.gym.enterprise.repositories.UserRepo;
import com.hiddu.gym.enterprise.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private GymBranchRepo gymBranchRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;

	@Override
	public UserDto createUser(UserDto userDto) {
		
//		GymBranch gymBranch = gymBranchRepo.findByGymCode(userDto.getGymBranchCode());
//		if(gymBranch == null)
//			throw new ResourceNotFoundException("GymBranch", "gymCode", userDto.getGymBranchCode());
//		
//		userDto.setPassword("password123");
//		User user = this.dtoToUser(userDto);
//		user.setUserCreatedDate(new Date());
//		user.setGymBranch(gymBranch);
//		User savedUser = this.userRepo.save(user);
//		return this.userToDto(savedUser);
		return this.registerNewUser(userDto);
	}

	@Override
	public UserDto updateeUser(UserDto userDto, Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","id", userId));
		
		Role role = this.roleRepo.findById(userDto.getRoleType()).get();
		
		user.getRoles().add(role);
		
		user.setUserName(userDto.getUserName());
		user.setEmailId(userDto.getEmailId());
		
		User updatedUser = this.userRepo.save(user);
		UserDto updatedUserDto = this.userToDto(updatedUser);
		
		return updatedUserDto;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		
		User user = this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","id", userId));
		UserDto userDto = this.userToDto(user);
		return userDto;
	}

	@Override
	public List<UserDto> getAllUsers() {
		
		List<User> users = this.userRepo.findAll();
		List<UserDto> usersDto = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		
		return usersDto;
	}

	@Override
	public void deleteUser(Integer userId) {
		
		User user = this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","id", userId));
		this.userRepo.delete(user);
	}
	
	@Override
	public List<UserDto> getUsersByGymBranch(String gymCode) {
		
		GymBranch gymBranch = gymBranchRepo.findByGymCode(gymCode);
		if(gymBranch == null)
			throw new ResourceNotFoundException("Gym Branch","gymCode", gymCode);
		
		List<User> users = this.userRepo.findByGymBranch(gymBranch);
		List<UserDto> usersDto = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		
		return usersDto;
	}
	
	private User dtoToUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		return user;
	}
	
	private UserDto userToDto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		return userDto;
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		
//		TODO: Need validation for creating user - example: Role - Platform Admin (only one user across system / platform ), Other roles - one phone number to many roles but one phone number to same role If already created not allowed 
		
		GymBranch gymBranch = gymBranchRepo.findByGymCode(userDto.getGymBranchCode());
		if(gymBranch == null)
			throw new ResourceNotFoundException("GymBranch", "gymCode", userDto.getGymBranchCode());
		
		Role role = this.roleRepo.findById(userDto.getRoleType()).get();
		
		if(role.getId() == UserEnum.USER_PLATFORM_ADMIN.getId()) {	//	USER_PLATFORM_ADMIN should not be more than one
			User userWithRoleAlreadyExists = this.userRepo.findUserByUserType(role.getName());
			if(userWithRoleAlreadyExists != null) {
				throw new ResourceAlreadyExistsException("User", "Role",role.getName());
			}
		}
		
		User userAlreadyExists = this.userRepo.findUserByPhoneNumberAndUserType(userDto.getPhoneNumber(), role.getName());
		if(userAlreadyExists != null) {
			throw new ResourceAlreadyExistsException("User", "Contact Number and Role", userDto.getPhoneNumber()+" , "+role.getName());
		}
		
		userDto.setPassword("password123");
		User user = this.dtoToUser(userDto);
		user.setUserCreatedDate(new Date());
		user.setGymBranch(gymBranch);		
		user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
		
		user.getRoles().add(role);
		
		User newUser = this.userRepo.save(user);
		
		return this.userToDto(newUser);
	}
}
