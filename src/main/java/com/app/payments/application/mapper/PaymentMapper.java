package com.app.payments.application.mapper;

import java.time.LocalDateTime;

import com.app.payments.domain.model.Payment;
import com.app.payments.domain.model.Purchase;
import com.app.payments.domain.model.dto.payments.PaymentCreateRequest;
import com.app.payments.domain.model.dto.payments.PaymentResponse;

public final  class PaymentMapper {
	
	 private PaymentMapper() {}
	 
	 public static Payment toEntity(PaymentCreateRequest r, Purchase purchase) {
	        return Payment.builder()
	                .purchase(purchase)
	                .amount(r.getAmount())
	                .paymentMethod(r.getMethod())
	                .paidAt(r.getPaidAt() != null ? r.getPaidAt() : LocalDateTime.now())
	                .note(r.getNote())
	                .build();
	    }

	    public static PaymentResponse toResponse(Payment p) {
	        return PaymentResponse.builder()
	                .id(p.getId())
	                .purchaseId(p.getPurchase().getId())
	                .amount(p.getAmount())
	                .method(p.getPaymentMethod())
	                .paidAt(p.getPaidAt())   
	                .note(p.getNote())
	                .build();
	    }

}
