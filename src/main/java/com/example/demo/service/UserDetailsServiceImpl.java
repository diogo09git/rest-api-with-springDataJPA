package com.example.demo.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.domain.ProdosUser;
import com.example.demo.repository.ProdosUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private ProdosUserRepository prodosUserRepository;
	
	public UserDetailsServiceImpl(ProdosUserRepository prodosUserRepository) {
		this.prodosUserRepository = prodosUserRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		ProdosUser prodosUser = prodosUserRepository.findByUsername(username);
		UserDetails user = new User(username, prodosUser.getPassword(), AuthorityUtils.createAuthorityList(prodosUser.getRole()));
		
		return user;
	}

	
}
