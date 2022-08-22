package com.hiddu.gym.enterprise.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hiddu.gym.enterprise.entities.User;
import com.hiddu.gym.enterprise.execptions.ResourceNotFoundException;
import com.hiddu.gym.enterprise.repositories.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//Loading user from database by username
		User  user = this.userRepo.findByPhoneNumber(username)
		.orElseThrow(()-> new ResourceNotFoundException("User", "Contact Number", username));
		
		return user;
	}

}
