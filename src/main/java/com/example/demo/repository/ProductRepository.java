package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.domain.Product;

public interface ProductRepository extends CrudRepository<Product, Integer>{
	
}
