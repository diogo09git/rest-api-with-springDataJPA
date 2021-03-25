package com.example.demo.domain;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import com.example.demo.controller.ProductController;

public class ProductModelAssembler extends RepresentationModelAssemblerSupport<Product, ProductModel>{

	public ProductModelAssembler() {
		super(ProductController.class, ProductModel.class);
	}
	
	@Override
	protected ProductModel instantiateModel(Product entity) {
		return new ProductModel(entity);
	}
	
	@Override
	public ProductModel toModel(Product entity) {
		return createModelWithId("products/" + entity.getId(), entity);
	}
	
}
