package com.example.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.CustomUserDetailService;
import com.example.service.UtilService;

@RestController
public class UtilController {

	@Autowired UtilService utilService ;
	
	@GetMapping("/util/encode/{string}")
	public String queryUserById(@PathVariable String string) {
		return utilService.encodeString(string) ;
	}
	
}
