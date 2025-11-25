package com.app.payments.domain.model.dto.payments;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.app.payments.domain.model.enums.PaymentMethod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PaymentResponse {
	
	private Long id;
    private Long purchaseId;
    private BigDecimal amount;
    private PaymentMethod method;
    private LocalDateTime paidAt;
    private String note;

}
