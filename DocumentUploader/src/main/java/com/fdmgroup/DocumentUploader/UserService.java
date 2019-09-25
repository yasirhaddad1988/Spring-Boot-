package com.fdmgroup.DocumentUploader;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;


@org.springframework.stereotype.Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;

	public void save(User user) {
		userRepo.save(user);
	}

	public User find(Long id) {
		Optional<User> user = userRepo.findById(id);
		if(user.isPresent()) {					
			return user.get();
		}
		return null;
	}
	
	public User find(String email) {
		Optional<User> user = userRepo.findByEmail(email);
		if(user.isPresent()) {					
			return user.get();
		}
		return null;
	}

	public Set<User> findByAccount(Account account) {
		Set<User> user = userRepo.findByAccount(account);
		return user;
	}
	
	
	

}
