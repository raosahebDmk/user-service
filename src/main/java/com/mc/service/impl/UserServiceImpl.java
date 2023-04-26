package com.mc.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mc.dao.Hotel;
import com.mc.dao.Rating;
import com.mc.dao.User;
import com.mc.exception.ResourceNotFound;
import com.mc.repositores.UserRepository;
import com.mc.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	

	@Autowired
	public UserRepository userRepository;
	
	@Autowired
	public RestTemplate restTemplate; 
	
	@Override
	public User saveUser(User user) {
		
		String randomUserId = UUID.randomUUID().toString();
		user.setUserId(randomUserId);
		
		return userRepository.save(user);
		
	}

	@Override
	public List<User> getAllUser() {
		
		 List<User> userList = userRepository.findAll();
		 userList.stream().forEach((e)->{ e.setRating(restTemplate.getForObject("http://RATING-SERVICE/rating/user/"+e.getUserId(), ArrayList.class));});
		 
		 return userList;
	}

	@Override
	public User getUser(String userId) {

		User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFound("User Not found in server: "+userId));
		
		Rating[] ratingList = restTemplate.getForObject("http://RATING-SERVICE/rating/user/"+userId, Rating[].class);
		
		List<Rating> ratings = Arrays.stream(ratingList).toList();
		
		
		List<Rating> ratingResp = ratings.stream().map((rating)->{
				ResponseEntity<Hotel> respHolte = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
				Hotel hotel = respHolte.getBody();
				rating.setHotel(hotel);
				return rating;
			}).collect(Collectors.toList());
		
		user.setRating(ratingResp);
		
		return user;
	}

	@Override
	public User updateUser(User user) {
		
		return userRepository.save(user);
	}

	@Override
	public void deleteUser(String userId) {
		userRepository.deleteById(userId);		
	}

	
	
	

}
