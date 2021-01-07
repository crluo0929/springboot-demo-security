package com.example.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.entity.User;

@Service
public class CustomAuthenticationProvider implements AuthenticationProvider{

	@Autowired CustomUserDetailService userDetailService ;
	@Autowired PasswordEncoder encoder ;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		//認證時從使用者端輸入的資料
		String username = authentication.getPrincipal().toString() ;
		String password = authentication.getCredentials().toString() ;
		UserDetails user = call3PartyAuthentication(username,password) ;
		
		return new UsernamePasswordAuthenticationToken(username,password,user.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

	//模擬呼叫第三方認證，實際上還是去查DB做驗證
	private UserDetails call3PartyAuthentication(String username,String password) throws AuthenticationException{
		try {
			//從DB撈資料出來組成UserDetails
			UserDetails userDetail = userDetailService.loadUserByUsername(username);
			//比對帳密
			boolean match = encoder.matches(password,userDetail.getPassword()) ;
			if(!match) throw new AuthenticationException("authentication fail!"){} ;
			return userDetail ;
		}catch(Exception e) {
			throw new AuthenticationException("authentication fail!!"){} ;
		}
		
	}
	
}
