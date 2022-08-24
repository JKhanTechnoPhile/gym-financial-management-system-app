package com.hiddu.gym.enterprise;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hiddu.gym.enterprise.config.AppConstants;
import com.hiddu.gym.enterprise.entities.Role;
import com.hiddu.gym.enterprise.enums.UserEnum;
import com.hiddu.gym.enterprise.repositories.RoleRepo;

@SpringBootApplication
public class GymFinancialManagementSystemAppApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(GymFinancialManagementSystemAppApplication.class, args);
	}
	
//	http://modelmapper.org/getting-started/
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("password123"));
	
		try {
			Role role = null;
			List<Role> roles = new ArrayList<>();
			for(UserEnum roleEnum : UserEnum.values()) {
				role = new Role();
				role.setId(roleEnum.getId());
				role.setName(roleEnum.getName());
				roles.add(role);
			}
			
//			
//			Role adminRole = new Role();
//			adminRole.setId(AppConstants.ADMIN_USER);
//			adminRole.setName("ADMIN_USER");
//			
//			Role normalRole = new Role();
//			normalRole.setId(AppConstants.NORMAL_USER);
//			normalRole.setName("NORMAL_USER");
//			
//			List<Role> roles = List.of(adminRole, normalRole);
			List<Role> result = this.roleRepo.saveAll(roles);
			
			result.forEach(savedRole -> System.out.println(savedRole.getName()));
			
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

}
