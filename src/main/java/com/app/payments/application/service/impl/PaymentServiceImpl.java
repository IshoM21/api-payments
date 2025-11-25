package com.app.payments.application.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.payments.application.mapper.PaymentMapper;
import com.app.payments.application.service.PaymentService;
import com.app.payments.domain.model.Purchase;
import com.app.payments.domain.model.dto.payments.PaymentCreateRequest;
import com.app.payments.domain.model.dto.payments.PaymentResponse;
import com.app.payments.domain.model.enums.PurchaseStatus;
import com.app.payments.domain.repository.PaymentsRepository;
import com.app.payments.domain.repository.PurchaseRepository;
import com.app.payments.presentation.advice.ConflictException;
import com.app.payments.presentation.advice.NotFoundException;

@Service
public class PaymentServiceImpl implements PaymentService{

	@Autowired
	private PurchaseRepository purchaseRepository;
	
	@Autowired
	private PaymentsRepository paymentsRepository;
	
	
	@Override
	public PaymentResponse create(Long purchaseId, PaymentCreateRequest request) {
		// TODO Auto-generated method stub
		Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new NotFoundException("Compra no encontrada"));

        if (purchase.getStatus() == PurchaseStatus.CANCELED) {
            throw new ConflictException("No se pueden registrar pagos en una compra cancelada");
        }

        BigDecimal alreadyPaid = paymentsRepository
                .sumAmountByPurchaseId(purchaseId)
                .orElse(BigDecimal.ZERO);

        BigDecimal newTotalPaid = alreadyPaid.add(request.getAmount());

        if (newTotalPaid.compareTo(purchase.getTotalAmount()) > 0) {
            throw new ConflictException("El pago excede el total de la compra");
        }

        var payment = PaymentMapper.toEntity(request, purchase);
        var saved = paymentsRepository.save(payment);

        // actualizar estado de la compra si quedó liquidada
        if (newTotalPaid.compareTo(purchase.getTotalAmount()) >= 0) {
            purchase.setStatus(PurchaseStatus.PAID);
            purchaseRepository.save(purchase);
        }

        return PaymentMapper.toResponse(saved);
	}

	@Override
	public List<PaymentResponse> listByPurchase(Long purchaseId) {
		// TODO Auto-generated method stub
		 // Valida que exista la compra (si no, 404)
        purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new NotFoundException("Compra no encontrada"));

        return paymentsRepository.findByPurchase_Id(purchaseId).stream()
                .map(PaymentMapper::toResponse)
                .toList();
	}

}
