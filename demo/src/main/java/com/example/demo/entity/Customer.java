package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	@Column(name = "user_name")
	private String userName;

	private String email;
	private String password;

	public Customer() {
	}

	public Customer(String name, String userName, String email, String password) {
		this.name = name;
		this.userName = userName;
		this.email = email;
		this.password = password;
	}

	public Customer(Integer id, String name, String userName, String email, String password) {
		this.id = id;
		this.name = name;
		this.userName = userName;
		this.email = email;
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getUserName() {
		return userName;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
}