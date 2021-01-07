package com.example.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.entity.User;
import com.example.repo.UserRepository;

@Service
public class UserService {

	@Autowired UserRepository userDao ;
	@Autowired PasswordEncoder encoder ;
	
	public User findUserById(int id){
		return userDao.findById(id).get() ;
	}
	
	public User findUserByName(String name) {
		return userDao.findByUsername(name) ;
	}
	
	public List<User> findByCity(String city){
		return StreamSupport
				.stream(userDao.findByCity(city).spliterator(), false)
				.collect(Collectors.toList());
	}
	
	public List<User> findAllUsers(){
		return StreamSupport
				.stream(userDao.findAll().spliterator(), false)
				.collect(Collectors.toList()) ;
	}
	
	public User createUser(User user) {
		//user's password should be encrypted before commit to database
		String encodePass = encoder.encode(user.getPassword()) ;
		user.setPassword(encodePass);
		User u = userDao.save(user) ;
		u.setPassword(null); //do not return frontend encoded password!
		return u ; 
	}
	
}
