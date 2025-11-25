package com.app.payments.domain.model.dto.payments;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.app.payments.domain.model.enums.PaymentMethod;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PaymentCreateRequest {

	@NotNull
    @DecimalMin("0.01")
    @Digits(integer = 12, fraction = 2)
    private BigDecimal amount;

    @NotNull
    private PaymentMethod method;

    // si viene null, en el servicio usaremos LocalDateTime.now()
    private LocalDateTime paidAt;

    @Size(max = 255)
    private String note;
    
}
