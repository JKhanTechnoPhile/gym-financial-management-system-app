package com.hiddu.gym.enterprise.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hiddu.gym.enterprise.entities.User;

public interface UserRepo extends JpaRepository<User, Integer>{

}
