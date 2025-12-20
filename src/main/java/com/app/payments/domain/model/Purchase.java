package com.app.payments.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.app.payments.domain.model.enums.PurchaseStatus;

import jakarta.persistence.Column;
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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "purchases")
@Setter
@Getter
@NoArgsConstructor 
@AllArgsConstructor 
@Builder
public class Purchase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Customer customer;
	
	 @NotBlank @Size(max = 200)
	private String description;
	
	@Column(nullable = false) @NotNull @DecimalMin(value = "0.00") @Digits(integer = 12, fraction = 2)
	private BigDecimal totalAmount;
	
	@Builder.Default
	private LocalDateTime createdAt = LocalDateTime.now();;
	
	@Builder.Default
	@Enumerated(EnumType.STRING)
	private PurchaseStatus status = PurchaseStatus.ACTIVO;
	
	// NUEVOS CAMPOS PARA PLAZOS

    @Builder.Default
    @Column(nullable = false)
    private boolean installmentEnabled = false;  // compra a plazos sí/no

    @Column
    private Integer installmentCount;            // número de plazos

    @Column(precision = 12, scale = 2)
    private BigDecimal installmentAmount;        // monto sugerido por plazo
    
    // 👇 NUEVO: usuario que registró la compra
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_id")
    private UserAccount createdBy;
    
}
