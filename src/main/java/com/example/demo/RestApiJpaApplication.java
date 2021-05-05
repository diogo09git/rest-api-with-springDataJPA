package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.domain.ProdosUser;
import com.example.demo.domain.Product;
import com.example.demo.repository.ProdosUserRepository;
import com.example.demo.repository.ProductRepository;

@SpringBootApplication
public class RestApiJpaApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(RestApiJpaApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(ProductRepository productRepository, ProdosUserRepository prodosUserRepository) {
		BCryptPasswordEncoder bcry = new BCryptPasswordEncoder();
		
		return args -> {
			productRepository.save(new Product("Nissan", "GTR", "Power"));
			prodosUserRepository.save(new ProdosUser("diogo", bcry.encode("abc"), "USER"));
			productRepository.save(new Product("Harley Dadvison", "Iron 883", "style"));
			productRepository.save(new Product("Volkswagen", "Fusca", "the ultra power car"));
		};
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
