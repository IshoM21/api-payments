package com.app.payments.application.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.payments.application.mapper.CustomerMapper;
import com.app.payments.application.service.CustomerService;
import com.app.payments.domain.model.Customer;
import com.app.payments.domain.model.dto.customer.CustomerCreateRequest;
import com.app.payments.domain.model.dto.customer.CustomerResponse;
import com.app.payments.domain.model.dto.customer.CustomerUpdateRequest;
import com.app.payments.domain.repository.CustomerRepository;
import com.app.payments.presentation.advice.ConflictException;
import com.app.payments.presentation.advice.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public List<CustomerResponse> listAll(){
		List<CustomerResponse> listCR = (List<CustomerResponse>) customerRepository.findAll()
				.stream()
				.map(CustomerMapper::toResponse)
				.toList();
		return  listCR;
	}
	
	@Override
	public CustomerResponse create(CustomerCreateRequest r) {
		// TODO Auto-generated method stub
		validateUnique(r.getEmail(), r.getPhone(), null);
		Customer entity = CustomerMapper.toEntity(r);
		return CustomerMapper.toResponse(customerRepository.save(entity));
	}

	@Override
	public CustomerResponse update(Long id, CustomerUpdateRequest r) {
		// TODO Auto-generated method stub
		Customer entity  = customerRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
		validateUnique(r.getEmail(), r.getPhone(), id);
		CustomerMapper.copyToEntity(r, entity);
		return CustomerMapper.toResponse(customerRepository.save(entity));
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		Customer entity = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
		customerRepository.delete(entity);
	}

	@Transactional(readOnly = true)
	@Override
	public CustomerResponse getById(Long id) {
		// TODO Auto-generated method stub
//		Customer entity = customerRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
//		return CustomerMapper.toResponse(entity);
		return customerRepository.findById(id)
				.map(CustomerMapper::toResponse)
				.orElseThrow(() -> new NotFoundException("Cliente No Encontrado"));
	}

	@Transactional(readOnly = true)
	@Override
	public Page<CustomerResponse> list(String q, int page, int size, String sort) {
		// TODO Auto-generated method stub
        Sort s = Sort.by(Sort.Order.asc((sort == null || sort.isBlank()) ? "name" : sort));
        Pageable pageable = PageRequest.of(page, size, s);
        Page<Customer> res = (q == null || q.isBlank())
        		? customerRepository.findAll(pageable)
        		: customerRepository.findByNameContainingIgnoreCase(q, pageable);
        return res.map(CustomerMapper::toResponse);
	}


	 private void validateUnique(String email, String phone, Long selfId) {
	        if (email != null && !email.isBlank()) {
	            boolean exists = customerRepository.existsByEmail(email);
	            if (exists && (selfId == null || customerRepository.findById(selfId)
	                    .filter(c -> email.equals(c.getEmail())).isEmpty())) {
	                throw new ConflictException("Email ya registrado");
	            }
	        }
	        if (phone != null && !phone.isBlank()) {
	            boolean exists = customerRepository.existsByPhone(phone);
	            if (exists && (selfId == null || customerRepository.findById(selfId)
	                    .filter(c -> phone.equals(c.getPhone())).isEmpty())) {
	                throw new ConflictException("Teléfono ya registrado");
	            }
	        }
	    }
}
