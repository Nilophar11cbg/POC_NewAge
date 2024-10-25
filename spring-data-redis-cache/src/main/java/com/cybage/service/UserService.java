package com.cybage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cybage.entity.User;
import com.cybage.exception.ResourceNotFoundException;
import com.cybage.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Cacheable(value = "usersCache", key = "#userId")
	public User getUserById(Long userId) {
		return userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("Resource Not found with id " +  userId));
	}
	
	public User saveUser(User user) {
		return userRepository.save(user);
	}
	
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	
	@CacheEvict(value= "usersCache", allEntries = true)
	public void clearCacheAll() {
		System.out.println("******* All Cache cleared *********");
	}
	
	@CacheEvict(value = "usersCache", key = "#userId")
	public void clearSingleUserIdCache(Long userId) {
		System.out.println(userId + " cleared from Cache!!");
	}
	
}
