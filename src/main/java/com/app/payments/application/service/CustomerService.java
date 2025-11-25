package com.app.payments.application.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.app.payments.domain.model.dto.customer.CustomerCreateRequest;
import com.app.payments.domain.model.dto.customer.CustomerResponse;
import com.app.payments.domain.model.dto.customer.CustomerUpdateRequest;

public interface CustomerService {
	List<CustomerResponse> listAll();
	CustomerResponse create(CustomerCreateRequest r);
	CustomerResponse update(Long id, CustomerUpdateRequest r);
	void delete(Long id);
	CustomerResponse getById(Long id);
	Page<CustomerResponse> list(String q, int page, int size, String sort);
}
