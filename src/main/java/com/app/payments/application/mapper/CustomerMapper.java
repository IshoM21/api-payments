package com.app.payments.application.mapper;

import com.app.payments.domain.model.Customer;
import com.app.payments.domain.model.dto.customer.CustomerCreateRequest;
import com.app.payments.domain.model.dto.customer.CustomerResponse;
import com.app.payments.domain.model.dto.customer.CustomerUpdateRequest;

public final class CustomerMapper {
	
	private CustomerMapper() {}
	
	public static Customer toEntity(CustomerCreateRequest r) {
		return Customer.builder()
				.name(r.getName())
				.email(r.getEmail())
				.phone(r.getPhone())
				.notes(r.getNotes())
				.build();
	}
	
	public static void copyToEntity(CustomerUpdateRequest r, Customer c) {
        c.setName(r.getName());
        c.setEmail(r.getEmail());
        c.setPhone(r.getPhone());
        c.setNotes(r.getNotes());
    }

    public static CustomerResponse toResponse(Customer c) {
        return CustomerResponse.builder()
                .id(c.getId())
                .name(c.getName())
                .email(c.getEmail())
                .phone(c.getPhone())
                .notes(c.getNotes())
                .build();
    }

}
