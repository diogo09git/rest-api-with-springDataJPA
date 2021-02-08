package com.example.demo.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Product;
import com.example.demo.repository.ProductRepository;

@RestController
@RequestMapping("/api")
public class ProductController {
	
	private ProductRepository productRepository;
	
	public ProductController(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@GetMapping("/product")
	public ResponseEntity<Iterable<Product>> findAll() {
		
		Optional<Iterable<Product>> products = Optional.of(productRepository.findAll());
		
		return products.map(i -> ResponseEntity.ok().body(i))
			.orElse(ResponseEntity.badRequest().build());
		
	}
	
	@GetMapping("/product/{id}")
	public ResponseEntity<Product> findById(@PathVariable Integer id) {
		
		Optional<Product> product = productRepository.findById(id);
		
		if(product.isPresent()) {
			return new ResponseEntity<Product>(product.get(), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/product/save")
	public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
		
		productRepository.save(product);
		return new ResponseEntity<Product>(product, HttpStatus.CREATED);
	}
	
	@PutMapping("/product/update/{id}")
	public ResponseEntity<Product> updateProducct(@PathVariable Integer id, @RequestBody Product newProduct) {
		
		return productRepository.findById(id)
				.map(product -> {
					product.setName(newProduct.getName());
					product.setDescription(newProduct.getDescription());
					product.setType(newProduct.getType());
					productRepository.save(product);
					return new ResponseEntity<Product>(product, HttpStatus.OK);
				})
				.orElse(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/product/delete/{id}")
	public ResponseEntity<Product> deleteProduct(@PathVariable Integer id) {
		
		return productRepository.findById(id)
				.map(product -> {
					productRepository.delete(product);
					return new ResponseEntity<Product>(HttpStatus.OK);
				})
				.orElse(ResponseEntity.notFound().build());
	}
}




