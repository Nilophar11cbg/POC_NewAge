package com.cybage.cache.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;

import com.cybage.entity.User;
import com.cybage.service.UserService;

import jakarta.annotation.PostConstruct;

@Component
public class CacheInitializer {

	@Autowired
	private UserService userService;
	
	@Autowired
	private CacheManager cacheManager;
	
	@PostConstruct
	public void preLoad() {
		Cache cache = cacheManager.getCache("usersCache");
		System.out.println("******** Initializing Cache");
		
		List<User> userList = userService.getAllUsers();
		
		for (User user : userList) {
			cache.put(user.getId(), user);
		}
	}
	
	@Scheduled(fixedRate = 15000, initialDelay = 15000)
	public void clearCache() {
		System.out.println("=======******** Clearing the cache *******=======");
		cacheManager.getCacheNames().stream().forEach(names-> cacheManager.getCache(names).clear());
	}
}
