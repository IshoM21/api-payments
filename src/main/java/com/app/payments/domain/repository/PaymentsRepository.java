package com.app.payments.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.payments.domain.model.Payment;

public interface PaymentsRepository extends JpaRepository<Payment, Long> {

	@Query("select coalesce(sum(p.amount), 0) from Payment p where p.purchase.id = :purchaseId")
	Optional<BigDecimal> sumAmountByPurchaseId(@Param("purchaseId") Long purchaseId);

	boolean existsByPurchase_Id(Long purchaseId); // útil para reglas de borrado

	List<Payment> findByPurchase_Id(Long purchaseId);
}
