package com.app.payments.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.app.payments.domain.model.Purchase;
import com.app.payments.domain.model.enums.PurchaseStatus;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

	Page<Purchase> findByCustomer_Id(Long customerId, Pageable pageable);

	Page<Purchase> findByStatus(PurchaseStatus status, Pageable pageable);

	Page<Purchase> findByCustomer_IdAndStatus(Long customerId, PurchaseStatus status, Pageable pageable);
}
