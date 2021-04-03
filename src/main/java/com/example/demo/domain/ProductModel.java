package com.example.demo.domain;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(value = "product", collectionRelation = "products")
public class ProductModel extends RepresentationModel<ProductModel> {

	private String brand;
	
	private String type;
	
	private String description;
	
	public ProductModel() {
		// TODO Auto-generated constructor stub
	}
	
	public ProductModel(Product product) {
		this.brand = product.getBrand();
		this.type = product.getType();
		this.description = product.getDescription();
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
