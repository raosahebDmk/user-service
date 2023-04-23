package com.mc.service;

import java.util.List;

import com.mc.dao.User;

public interface UserService {
	
	// create
	User saveUser(User user);
	
	// get all users list
	List<User> getAllUser();
	
	// get single user using userId
	User getUser(String userId);

}
