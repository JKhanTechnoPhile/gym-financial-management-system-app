package com.hiddu.gym.enterprise.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hiddu.gym.enterprise.entities.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Integer>{

}
