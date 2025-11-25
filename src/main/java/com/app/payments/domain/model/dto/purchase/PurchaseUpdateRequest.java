package com.app.payments.domain.model.dto.purchase;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PurchaseUpdateRequest {
	
	@NotBlank @Size(max = 200)
    private String description;

    @NotNull @DecimalMin(value = "0.00") @Digits(integer = 12, fraction = 2)
    private BigDecimal totalAmount;

}
