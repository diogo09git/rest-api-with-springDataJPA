package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.ProdosUser;
import com.example.demo.dto.AccountCredentials;
import com.example.demo.repository.ProdosUserRepository;
import com.example.demo.security.JWTAuthenticationService;

@RestController
public class AuthenticationController {

	AuthenticationManager authenticationManager;
	
	JWTAuthenticationService jwtAuthenticationService;
	
	ProdosUserRepository prodosUserRepository;

	public AuthenticationController(AuthenticationManager authenticationManager,
			JWTAuthenticationService jwtAuthenticationService, ProdosUserRepository prodosUserRepository) {
		this.authenticationManager = authenticationManager;
		this.jwtAuthenticationService = jwtAuthenticationService;
		this.prodosUserRepository = prodosUserRepository;
	}
	
	@PostMapping("/auth/login")
	public ResponseEntity<Map<Object, Object>> signin(@RequestBody AccountCredentials credentials) {
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword()));
			
			List<ProdosUser> list = new ArrayList<ProdosUser>();
			list.add(prodosUserRepository.findByUsername(credentials.getUsername()));
			
			String token = jwtAuthenticationService.createToken(credentials.getUsername(), list);
			
			Map<Object, Object> model = new HashMap<Object, Object>();
			model.put("username", credentials.getUsername());
			model.put("token", token);
			return ResponseEntity.ok(model);
			
		} catch (AuthenticationException e) {
			throw new BadCredentialsException("Invalid username/password supplied");
		}
	}
}
