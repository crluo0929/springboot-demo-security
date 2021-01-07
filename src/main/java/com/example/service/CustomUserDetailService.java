package com.example.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
		User user = Optional.ofNullable(userDao.findByUsername(username)).get().orElse(new User()) ;
		List<GrantedAuthority> authList = 
				Arrays.asList(user.getRoles().split(","))
				.stream()
				.map(role -> new SimpleGrantedAuthority("ROLE_"+role.toUpperCase()))
				.collect(Collectors.toList()) ;
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(), authList);
	}
	
}
