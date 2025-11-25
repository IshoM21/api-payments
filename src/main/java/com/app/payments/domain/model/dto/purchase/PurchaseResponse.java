package com.app.payments.domain.model.dto.purchase;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.app.payments.domain.model.enums.PurchaseStatus;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PurchaseResponse {

	private Long id;
    private Long customerId;
    private String customerName;
    private String description;
    private BigDecimal totalAmount;
    private BigDecimal paidAmount;      // suma de pagos
    private BigDecimal remainingAmount; // total - paid
    private PurchaseStatus status;
    private LocalDateTime createdAt;
	
}
