package com.example.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.User;
import com.example.service.JWTTokenProvider;
import com.example.service.UserService;

@RestController
public class UserController {

	@Autowired UserService userService ;
	@Autowired AuthenticationManager authMgr ;
	@Autowired JWTTokenProvider jwtProvider ;
	@Autowired UserDetailsService detailsService ; 
	
	@GetMapping("/user/id/{id}")
	public User queryUserById(@PathVariable int id) {
		return userService.findUserById(id) ;
	}
	
	@GetMapping("/user/city/{city}")
	public List<User> queryUsersLiveInCity(@PathVariable String city){
		return userService.findByCity(city);
	}
	
	@GetMapping("/user/name/{name}")
	@PreAuthorize("hasRole('ADMIN')")
	public User queryUserByUsername(@PathVariable("name") String username) {
		return userService.findUserByName(username) ;
	}
	
	@PostMapping("/user/create")
	public User createUser(@RequestBody User user) {
		return userService.createUser(user) ;
	}
	
	
	@PostMapping("/auth")
	public String auth(@RequestBody User user) {
		String username = user.getUsername() ;
		String password = user.getPassword() ;
		//建立token
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
		//認證後保存
		Authentication authentication = authMgr.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		//通過認證產生jwt token
		UserDetails u = detailsService.loadUserByUsername(username);
		String jwtToken = jwtProvider.generateToken(u);
		return jwtToken ;
	}

}
