package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.filter.JWTTokenFilter;
import com.example.service.CustomAuthenticationProvider;
import com.example.service.CustomUserDetailService;
import com.example.service.JWTTokenProvider;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired PasswordEncoder encoder ;
	@Autowired CustomUserDetailService userDetail ;
	@Autowired CustomAuthenticationProvider authProvider ;
	@Autowired JWTTokenProvider tokenProvider ;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		JWTTokenFilter tokenFilter = new JWTTokenFilter(userDetail, tokenProvider) ;
		
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET,"/user/id/**").hasAnyRole("ADMIN")
		.antMatchers(HttpMethod.GET,"/user/name/**").permitAll() //@PreAuthorize at method
		.antMatchers(HttpMethod.GET, "/user/city/**").authenticated()
		.antMatchers("/h2-console/**").hasAnyRole("ADMIN","USER")
		.antMatchers("/util/**").permitAll()
		.antMatchers("/auth").permitAll()
		.anyRequest().authenticated()
		.and()
		.headers().frameOptions().disable() //for h2-console
		.and()
		.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
//		.formLogin()
//		.and()
		.csrf().disable();
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userDetail).passwordEncoder(encoder) ;
		auth.authenticationProvider(authProvider);
	}
	
//	@Override
//	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers("/h2-console/**").and().debug(true) ;
//	}

	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
}
