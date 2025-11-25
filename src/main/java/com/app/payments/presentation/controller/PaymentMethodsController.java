package com.app.payments.presentation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.payments.domain.model.enums.PaymentMethod;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/payment-methods")
@Tag(name = "Payment Methods", description = "Métodos de pago disponibles")
public class PaymentMethodsController {

	@GetMapping
	@Operation(summary = "Listar métodos de pago", description = "Devuelve todos los métodos de pago disponibles para registrar un pago.")
	public PaymentMethod[] list() {
		return PaymentMethod.values();
	}
}
