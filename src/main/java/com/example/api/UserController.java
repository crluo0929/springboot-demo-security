package com.example.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.User;
import com.example.service.UserService;

@RestController
public class UserController {

	@Autowired UserService userService ;
	
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

}
