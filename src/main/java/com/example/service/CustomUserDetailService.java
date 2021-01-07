package com.example.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.entity.User;
import com.example.repo.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired UserRepository userDao ;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUsername(username);
		return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),Collections.emptyList());
	}
	
}
