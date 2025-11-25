package com.app.payments.application.mapper;

import java.math.BigDecimal;

import com.app.payments.domain.model.Purchase;
import com.app.payments.domain.model.dto.purchase.PurchaseCreateRequest;
import com.app.payments.domain.model.dto.purchase.PurchaseResponse;
import com.app.payments.domain.model.dto.purchase.PurchaseUpdateRequest;

public final class PurchaseMapper {

	
	private PurchaseMapper() {}
	
	public static Purchase toEntity(PurchaseCreateRequest r) {
		return Purchase.builder().description(r.getDescription()).totalAmount(r.getTotalAmount()).build();
	}

	public static void copyToEntity(PurchaseUpdateRequest r, Purchase e) {
        e.setDescription(r.getDescription());
        e.setTotalAmount(r.getTotalAmount());
    }
	
	public static PurchaseResponse toResponse(Purchase e, BigDecimal paid) {
        BigDecimal paidSafe = paid == null ? BigDecimal.ZERO : paid;
        BigDecimal remaining = e.getTotalAmount().subtract(paidSafe);

        return PurchaseResponse.builder()
                .id(e.getId())
                .customerId(e.getCustomer().getId())
                .customerName(e.getCustomer().getName())
                .description(e.getDescription())
                .totalAmount(e.getTotalAmount())
                .paidAmount(paidSafe)
                .remainingAmount(remaining.max(BigDecimal.ZERO))
                .status(e.getStatus())
                .createdAt(e.getCreatedAt())
             // 👇 nuevos campos
                .installmentEnabled(e.isInstallmentEnabled())
                .installmentCount(e.getInstallmentCount())
                .installmentAmount(e.getInstallmentAmount())
                .build();
    }
}
