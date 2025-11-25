package com.app.payments.application.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.payments.application.mapper.PurchaseMapper;
import com.app.payments.application.service.PurchaseService;
import com.app.payments.domain.model.Customer;
import com.app.payments.domain.model.Purchase;
import com.app.payments.domain.model.dto.PageResponse;
import com.app.payments.domain.model.dto.purchase.PurchaseCreateRequest;
import com.app.payments.domain.model.dto.purchase.PurchaseResponse;
import com.app.payments.domain.model.dto.purchase.PurchaseUpdateRequest;
import com.app.payments.domain.model.enums.PurchaseStatus;
import com.app.payments.domain.repository.CustomerRepository;
import com.app.payments.domain.repository.PaymentsRepository;
import com.app.payments.domain.repository.PurchaseRepository;
import com.app.payments.presentation.advice.ConflictException;
import com.app.payments.presentation.advice.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PurchaseServiceImpl implements PurchaseService{

	@Autowired
	private PurchaseRepository purchaseRepository;
	@Autowired	
	private CustomerRepository customerRepository;
	@Autowired
	private PaymentsRepository paymentsRepository;
	
	@Override
	public PurchaseResponse create(PurchaseCreateRequest request) {
		Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));

        Purchase entity = PurchaseMapper.toEntity(request);
        entity.setCustomer(customer);

        Purchase saved = purchaseRepository.save(entity);
        BigDecimal paid = paymentsRepository.sumAmountByPurchaseId(saved.getId()).orElse(BigDecimal.ZERO);
        updateStatusByAmounts(saved, paid);

        return PurchaseMapper.toResponse(saved, paid);
	}

	@Override
	public PurchaseResponse update(Long id, PurchaseUpdateRequest request) {
		// TODO Auto-generated method stub
		Purchase entity = purchaseRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Compra no encontrada"));
		BigDecimal paid = paymentsRepository.sumAmountByPurchaseId(id).orElse(BigDecimal.ZERO);
		if(request.getTotalAmount().compareTo(paid) < 0) {
			throw new ConflictException("El total no puede ser menor a lo ya pagado (" + paid + ")");
		}
		
		PurchaseMapper.copyToEntity(request, entity);
		updateStatusByAmounts(entity, paid);
		
		Purchase saved = purchaseRepository.save(entity);
		return PurchaseMapper.toResponse(saved, paid);
	}

	@Override
	public void cancel(Long id) {
		// TODO Auto-generated method stub
		Purchase entity = purchaseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Compra no encontrada"));
        entity.setStatus(PurchaseStatus.CANCELED);
        purchaseRepository.save(entity);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		Purchase entity = purchaseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Compra no encontrada"));
        if (paymentsRepository.existsByPurchase_Id(id)) {
            throw new ConflictException("No se puede eliminar: la compra tiene pagos registrados");
        }
        purchaseRepository.delete(entity);
	}

	 @Transactional(readOnly = true)
	@Override
	public PurchaseResponse getById(Long id) {
		// TODO Auto-generated method stub
		 Purchase e = purchaseRepository.findById(id)
	                .orElseThrow(() -> new NotFoundException("Compra no encontrada"));
	        BigDecimal paid = paymentsRepository.sumAmountByPurchaseId(id).orElse(BigDecimal.ZERO);
	        updateStatusByAmounts(e, paid); // solo cálculo en memoria para la respuesta
	        return PurchaseMapper.toResponse(e, paid);
	}

	@Override
	public PageResponse<PurchaseResponse> list(Long customerId, PurchaseStatus status, int page, int size,
			String sort) {
		// TODO Auto-generated method stub
		Sort s = Sort.by(Sort.Order.desc((sort == null || sort.isBlank()) ? "createdAt" : sort));
        Pageable pageable = PageRequest.of(page, size, s);

        Page<Purchase> pageResult;
        if (customerId != null && status != null) {
            pageResult = purchaseRepository.findByCustomer_IdAndStatus(customerId, status, pageable);
        } else if (customerId != null) {
            pageResult = purchaseRepository.findByCustomer_Id(customerId, pageable);
        } else if (status != null) {
            pageResult = purchaseRepository.findByStatus(status, pageable);
        } else {
            pageResult = purchaseRepository.findAll(pageable);
        }

        // mapear cada purchase a response con sus totales
        var content = pageResult.getContent().stream()
                .map(p -> PurchaseMapper.toResponse(
                        p,
                        paymentsRepository.sumAmountByPurchaseId(p.getId()).orElse(BigDecimal.ZERO)
                )).toList();

        return PageResponse.<PurchaseResponse>builder()
                .content(content)
                .page(pageResult.getNumber())
                .size(pageResult.getSize())
                .totalElements(pageResult.getTotalElements())
                .totalPages(pageResult.getTotalPages())
                .last(pageResult.isLast())
                .build();
	}
	
	/** Marca PAID si total <= pagado y no está cancelada; si no, ACTIVE */
    private void updateStatusByAmounts(Purchase e, BigDecimal paid) {
        if (e.getStatus() == PurchaseStatus.CANCELED) return;
        if (e.getTotalAmount().compareTo(paid) <= 0) {
            e.setStatus(PurchaseStatus.PAID);
        } else {
            e.setStatus(PurchaseStatus.ACTIVE);
        }
    }

	
}
