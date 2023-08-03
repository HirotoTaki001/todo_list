package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	List<Customer> findByEmailAndPassword(String email, String password);

	List<Customer> findByUserNameAndPassword(String name, String password);
}
