package com.app.payments.domain.model.dto;

import java.math.BigDecimal;

import com.app.payments.domain.model.enums.PurchaseStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class PurchaseSummaryDTO {

	private Long id;
    private String customerName;
    private String description;
    private BigDecimal totalAmount;
    private BigDecimal paidAmount;
    private BigDecimal remainingAmount;
    private PurchaseStatus status;
}
