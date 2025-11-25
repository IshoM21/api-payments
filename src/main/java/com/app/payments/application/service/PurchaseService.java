package com.app.payments.application.service;

import com.app.payments.domain.model.dto.PageResponse;
import com.app.payments.domain.model.dto.purchase.InstallmentSimulationRequest;
import com.app.payments.domain.model.dto.purchase.InstallmentSimulationResponse;
import com.app.payments.domain.model.dto.purchase.PurchaseCreateRequest;
import com.app.payments.domain.model.dto.purchase.PurchaseResponse;
import com.app.payments.domain.model.dto.purchase.PurchaseUpdateRequest;
import com.app.payments.domain.model.enums.PurchaseStatus;

public interface PurchaseService {

	PurchaseResponse create(PurchaseCreateRequest request);

	PurchaseResponse update(Long id, PurchaseUpdateRequest request);

	void cancel(Long id); // cambio de estado a CANCELED

	void delete(Long id); // opcional: permitir solo si no hay pagos

	PurchaseResponse getById(Long id);

	PageResponse<PurchaseResponse> list(Long customerId, PurchaseStatus status, int page, int size, String sort);
	
	InstallmentSimulationResponse simulationInstallments(InstallmentSimulationRequest r);
}
