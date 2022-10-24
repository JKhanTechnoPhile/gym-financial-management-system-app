package com.hiddu.gym.enterprise.security;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.hiddu.gym.enterprise.entities.Role;
import com.hiddu.gym.enterprise.entities.User;
import com.hiddu.gym.enterprise.execptions.ResourceNotFoundException;
import com.hiddu.gym.enterprise.repositories.RoleRepo;
import com.hiddu.gym.enterprise.repositories.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
        String[] usernameAndRoleType = StringUtils.split(username, String.valueOf(Character.LINE_SEPARATOR));

      //if the String arrays is empty or size is not equal to 2, let's throw exception
        if (Objects.isNull(usernameAndRoleType) || usernameAndRoleType.length != 2) {
            throw new ResourceNotFoundException("User", "Contact Number and selected Role", username);
        }
        final String userName = usernameAndRoleType[0];
        final String roleType = usernameAndRoleType[1];
        
        Role role = this.roleRepo.findById(Integer.valueOf(roleType)).get();
        
		
		//Loading user from database by username and role
//		User  user = this.userRepo.findByPhoneNumber(username)
//		.orElseThrow(()-> new ResourceNotFoundException("User", "Contact Number", username));
        
        User user = this.userRepo.findUserByPhoneNumberAndUserType(userName, role.getName());
        if(user == null)
        	throw new ResourceNotFoundException("User", "Contact Number and selected Role", username);
		
		return user;
	}

}
