package com.mc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mc.dao.User;
import com.mc.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	public UserService userService;
	
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user)
	{
		User user1 = userService.saveUser(user);		
		return ResponseEntity.status(HttpStatus.CREATED).body(user1);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<User> getUser(@PathVariable String userId)
	{
		User user = userService.getUser(userId);
		return ResponseEntity.ok(user);
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers()
	{
		System.out.println("start..");
		List<User> userList = userService.getAllUser();
		System.out.println("get data"+userList);
		return ResponseEntity.ok(userList);
	}
	
	@PutMapping
	public ResponseEntity<User> updateUser(@RequestBody User user)
	{
		User resp = userService.updateUser(user);
		return ResponseEntity.ok(resp);
	}
	
	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable String userId)
	{
		userService.deleteUser(userId);
	}
	
}


