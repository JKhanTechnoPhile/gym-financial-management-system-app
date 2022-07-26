package com.hiddu.gym.enterprise;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GymFinancialManagementSystemAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(GymFinancialManagementSystemAppApplication.class, args);
	}
	
//	http://modelmapper.org/getting-started/
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
