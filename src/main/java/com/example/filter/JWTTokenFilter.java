package com.example.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.example.service.JWTTokenProvider;

public class JWTTokenFilter extends GenericFilterBean{

	private final static String XAUTH_TOKEN_HEADER_NAME = "api-token" ;
	private UserDetailsService userDetailsService ;
	private JWTTokenProvider provider ;
	
	public JWTTokenFilter(UserDetailsService userDetailsService, JWTTokenProvider provider ) {
		this.userDetailsService = userDetailsService ;
		this.provider = provider ;
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String authToken = httpServletRequest.getHeader(XAUTH_TOKEN_HEADER_NAME);
		if (StringUtils.hasText(authToken)) {
			String username = null ;
			try {
				username = provider.getUsernameFromToken(authToken) ;
				UserDetails details = this.userDetailsService.loadUserByUsername(username);
				boolean valid = provider.validateToken(authToken, details);
                                //其實驗證這一段應該是透過 jwt sceret string 去驗證token是否合法就好了
                                //使用者資訊可能可以存在token中，但是要小心使用者資訊裡的role的資訊被串改
                                //可能要補檢查Role這段資訊
				if(valid) {
					UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(details, details.getPassword(), details.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(token);
				}
			}catch(Exception e) {
				//交給下一個filter處理
			}
		}
		chain.doFilter(request, response);
		
	}
	
}
