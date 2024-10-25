package com.cybage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cybage.entity.User;
import com.cybage.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping
	public List<User> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable Long userId) {
		User user = userService.getUserById(userId);
		return new ResponseEntity<>(user, HttpStatus.FOUND);
	}
	
	@PostMapping("/saveuser")
	public ResponseEntity<User> saveUser(@RequestBody User user) {
		return ResponseEntity.ok(userService.saveUser(user));
	}
	
	
	@GetMapping("/clearAllCache")
	public String clearAllCache() {
		userService.clearCacheAll();
		return "All cache Cleared";
	}
	
	@GetMapping("/clearusercache/{userId}")
	public String clearSingleUserIdCache(@PathVariable Long userId) {
		userService.clearSingleUserIdCache(userId);
		return "************ UserId " + userId + " Cleared from Cache **********";
	}
}
