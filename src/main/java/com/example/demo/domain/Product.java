package com.example.demo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Product {

	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(nullable = false)
	private String brand;
	
	@Column(nullable = false)
	private String type;
	
	@Column(nullable = false)
	private String description;

	public Product() {
	}
	
	public Product(String brand, String type, String description) {
		this.brand = brand;
		this.type = type;
		this.description = description;
	}
	
	public Integer getId() {
		return id;
	}

	public String getName() {
		return brand;
	}

	public void setName(String name) {
		this.brand = name;
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
