package com.mc.controller;

import java.util.List;

import org.hibernate.internal.build.AllowSysOut;
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

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.Builder;

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
	int i = 1;
	@GetMapping("/{userId}")
	//@CircuitBreaker(name = "ratingHotelBracker", fallbackMethod = "ratingHotelFallBack")
	@Retry(name = "ratingHotelBracker", fallbackMethod = "ratingHotelFallBack")
	public ResponseEntity<User> getUser(@PathVariable String userId)
	{
		User user = userService.getUser(userId);
		System.out.println("**** count : "+i);
		i++;
		return ResponseEntity.ok(user);
	}
	
	public ResponseEntity<User> ratingHotelFallBack(String userId, Exception ex)
	{
		System.out.println("FallBack is execute because service is down..."+ex.getMessage());
		User user = User.builder()
				.name("Dummy")
				.about("This is Dummy response")
				.email("dummy@dummy.coom")
				.userId("12345")
				.build();
		return ResponseEntity.status(HttpStatus.OK).body(user);
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



