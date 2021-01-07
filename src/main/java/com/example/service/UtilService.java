package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UtilService {

	@Autowired PasswordEncoder encoder ;
	
	public String encodeString(String password) {
		return encoder.encode(password) ;
	}
	
}
