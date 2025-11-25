package com.app.payments.application.service;

import java.util.List;

import com.app.payments.domain.model.dto.payments.PaymentCreateRequest;
import com.app.payments.domain.model.dto.payments.PaymentResponse;

public interface PaymentService {
	
	PaymentResponse create(Long purchaseId, PaymentCreateRequest request);

    List<PaymentResponse> listByPurchase(Long purchaseId);

}
