package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.service.CustomUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired PasswordEncoder encoder ;
//	@Autowired CustomUserDetailService userDetail ;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET,"/user/id/**").authenticated()
		.antMatchers(HttpMethod.GET,"/user/name/**").permitAll()
		.antMatchers(HttpMethod.POST, "/user/**").authenticated()
		.antMatchers("/h2-console/**").permitAll()
		.antMatchers("/util/**").permitAll()
		.anyRequest().permitAll()
		.and()
		.headers().frameOptions().disable()
		.and()
		.formLogin()
		.and()
		.csrf().disable();
		
	}
	
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userDetail).passwordEncoder(encoder) ;
//	}
	
//	@Override
//	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers("/h2-console/**").and().debug(true) ;
//		
//	}

	
}
