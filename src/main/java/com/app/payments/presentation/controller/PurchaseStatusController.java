package com.app.payments.presentation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.payments.domain.model.enums.PurchaseStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/purchase-status")
@Tag(name = "Purchase Status", description = "Estados disponibles para una compra")
public class PurchaseStatusController {
	@GetMapping
	@Operation(summary = "Listar estados de compra", description = "Devuelve todos los estados válidos para una compra.")
	public PurchaseStatus[] list() {
		return PurchaseStatus.values();
	}
}
