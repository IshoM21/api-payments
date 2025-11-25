package com.app.payments.domain.model.dto.purchase;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstallmentSimulationResponse {

	private BigDecimal totalAmount;
	private Integer installmentCount;
	
	private BigDecimal installmentAmount;
	
}
