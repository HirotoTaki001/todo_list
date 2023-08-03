package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	@Column(name = "customer_id")
	private Integer customerId;

	public Category() {
	}

	public Category(String name, Integer customerId) {
		this.name = name;
		this.customerId = customerId;
	}

	public Category(Integer id, String name, Integer customerId) {
		this.id = id;
		this.name = name;
		this.customerId = customerId;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Integer getCustomerId() {
		return customerId;
	}
}
