package com.app.payments.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.app.payments.domain.model.enums.PaymentMethod;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "payments")
@Setter
@Getter
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Purchase purchase;
	

	@NotNull
    @DecimalMin("0.01")
    @Digits(integer = 12, fraction = 2)
	private BigDecimal amount;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private PaymentMethod paymentMethod;

	@NotNull
	private LocalDateTime paidAt;
	
	@Size(max = 255)
	private String note;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by_user_id")
	private UserAccount createdBy;
}
