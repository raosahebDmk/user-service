package com.mc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.mc.dao.User;
import com.mc.exception.ResourceNotFound;
import com.mc.repositores.UserRepository;
import com.mc.service.UserService;

public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;
	
	@Override
	public User saveUser(User user) {
		
		return userRepository.save(user);
		
	}

	@Override
	public List<User> getAllUser() {
		
		return userRepository.findAll();
	}

	@Override
	public User getUser(String userId) {

		return userRepository.findById(userId).orElseThrow(()-> new ResourceNotFound("User Not found in server: "+userId));
	}
	
	

}
