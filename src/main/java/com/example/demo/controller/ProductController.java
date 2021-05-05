package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.hateoas.CollectionModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Product;
import com.example.demo.domain.ProductModel;
import com.example.demo.domain.ProductModelAssembler;
import com.example.demo.repository.ProductRepository;

@RestController
@RequestMapping("/api")
public class ProductController {
	
	private ProductRepository productRepository;
	
	public ProductController(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
//	@GetMapping("/products")
//	public ResponseEntity<Iterable<Product>> findAll() {
//		
//		Optional<Iterable<Product>> products = Optional.of(productRepository.findAll());
//
//		return products.map(i -> ResponseEntity.ok().body(i))
//			.orElse(ResponseEntity.badRequest().build());
//		
//	}
	
	@GetMapping("/products")
	public ResponseEntity<CollectionModel<ProductModel>> findAll() {
		
		List<Product> products = new ArrayList<Product>();
		productRepository.findAll().forEach(i -> products.add(i));
		
		CollectionModel<ProductModel> productModelList = new ProductModelAssembler().toCollectionModel(products);
		productModelList.add(linkTo(methodOn(ProductController.class).findAll()).withRel("products"));
		
		return ResponseEntity.ok(productModelList);
	}

	@GetMapping("/products/{id}")
	public ResponseEntity<ProductModel> findById(@PathVariable Integer id) {
		
//		return productRepository.findById(id)
//			.map(pro -> {
//				return ResponseEntity.ok(new ProductModelAssembler().toModel(pro));
//			})
//			.orElse(ResponseEntity.notFound().build());
		
		return productRepository.findById(id)
				.map(pro -> {
					ProductModel product = new ProductModelAssembler().toModel(pro);
					product.add(linkTo(methodOn(ProductController.class).findAll()).withRel("products"));
					return ResponseEntity.ok(product);
				}).orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping("/products")
	public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
		
		productRepository.save(product);
		return new ResponseEntity<Product>(product, HttpStatus.CREATED);
	}
	
//	@PutMapping("/product/update/{id}")
//	public ResponseEntity<Product> updateProducct(@PathVariable Integer id, @RequestBody Product newProduct) {
//		
//		return productRepository.findById(id)
//				.map(product -> {
//					product.setName(newProduct.getName());
//					product.setDescription(newProduct.getDescription());
//					product.setType(newProduct.getType());
//					productRepository.save(product);
//					return new ResponseEntity<Product>(product, HttpStatus.OK);
//				})
//				.orElse(ResponseEntity.notFound().build());
//	}
	
	//(PatchMapping) Update total or partial data on the server
	
	@PatchMapping("/products/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @RequestBody Product newProduct) {
		
		return productRepository.findById(id)
				.map(product -> {
					if(newProduct.getBrand() != null) {
						product.setBrand(newProduct.getBrand());
					}
					if(newProduct.getDescription() != null) {
						product.setDescription(newProduct.getDescription());
					}
					if(newProduct.getType() != null) {
						product.setType(newProduct.getType());
					}
					
					productRepository.save(product);
					return new ResponseEntity<Product>(product, HttpStatus.OK);
					
				}).orElse(ResponseEntity.notFound().build());
		
	}
	
	@DeleteMapping("/products/{id}")
	public ResponseEntity<Product> deleteProduct(@PathVariable Integer id) {
		
		return productRepository.findById(id)
			.map(product -> {
				productRepository.delete(product);
				return new ResponseEntity<Product>(HttpStatus.OK);
			})
			.orElse(ResponseEntity.notFound().build());
	}
}




