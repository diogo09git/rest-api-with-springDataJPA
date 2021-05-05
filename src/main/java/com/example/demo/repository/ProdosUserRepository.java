package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.demo.domain.ProdosUser;

public interface ProdosUserRepository extends CrudRepository<ProdosUser, Integer>{
	
	ProdosUser findByUsername(String username);
	
}
