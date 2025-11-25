package com.app.payments.domain.model.dto.purchase;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
public class InstallmentSimulationRequest {

	@NotNull
	@DecimalMin("0.001")
	private BigDecimal totalAmount;
	
	@NotNull
	@Min(1)
	private Integer installmentCount;
	
}
