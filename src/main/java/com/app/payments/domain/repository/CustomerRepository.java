package com.app.payments.domain.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.app.payments.domain.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

	boolean existsByEmail(String email);
	boolean existsByPhone(String phone);
	
	Page<Customer> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
