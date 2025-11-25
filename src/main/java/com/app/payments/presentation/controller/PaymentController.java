package com.app.payments.presentation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.payments.application.service.PaymentService;
import com.app.payments.domain.model.dto.payments.PaymentCreateRequest;
import com.app.payments.domain.model.dto.payments.PaymentResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/purchases/{purchaseId}/payments")
@RequiredArgsConstructor
@Tag(name = "Payments", description = "Pagos de una compra")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	@PostMapping
	@Operation(summary = "Registrar un pago", description = "Registra un nuevo pago (abono) para una compra existente.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del pago a registrar", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", examples = {
			@io.swagger.v3.oas.annotations.media.ExampleObject(name = "Ejemplo registrar pago", value = """
					{
					  "amount": 2000.00,
					  "method": "EFECTIVO",
					  "paidAt": null,
					  "note": "Abono inicial"
					}
					""") })))
	public ResponseEntity<?> create(@PathVariable Long purchaseId,
			@Validated @RequestBody PaymentCreateRequest request) {

		PaymentResponse created = paymentService.create(purchaseId, request);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@GetMapping
	@Operation(summary = "Listar pagos de una compra", description = "Devuelve todos los pagos registrados para la compra indicada.")
	public List<PaymentResponse> list(@PathVariable Long purchaseId) {
		return paymentService.listByPurchase(purchaseId);
	}

}
