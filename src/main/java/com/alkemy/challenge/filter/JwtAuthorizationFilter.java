package com.alkemy.challenge.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.alkemy.challenge.services.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
	private static final Log logger = LogFactory.getLog(JwtAuthorizationFilter.class);
	private static final String AUTHORIZATION = "Authorization";
	@Autowired
	JwtService jwtService;

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String authHeader = request.getHeader(AUTHORIZATION);
		logger.info("Processing... AUTHORIZATION: " + authHeader);

		if (jwtService.isBearer(authHeader)) { 
				try {
					List<GrantedAuthority> authorities = jwtService.roles(authHeader).stream()
							.map(SimpleGrantedAuthority::new).collect(Collectors.toList());

					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							jwtService.user(authHeader), null, authorities);
					SecurityContextHolder.getContext().setAuthentication(authentication);
					
					chain.doFilter(request, response);
					logger.info("Token received successfully");
				} catch (Exception e) {
					log.error("Error : {}", e.getMessage());
					response.setHeader("error", e.getMessage());
					response.setStatus(HttpStatus.FORBIDDEN.value());
					response.setContentType("application/json");
					Map<String, String> error = new HashMap<>();
					error.put("error_message", e.getMessage());
					new ObjectMapper().writeValue(response.getOutputStream(), error);
				}
		
		} else {
			logger.info("Authorization malformaded in bearer");
			chain.doFilter(request, response);
			
		}
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String path = request.getRequestURI();
		Boolean loginNotFilter = ("/api/auth/login".equals(path)) ? true : false;

		return loginNotFilter; // && () ... etc
	}

}
