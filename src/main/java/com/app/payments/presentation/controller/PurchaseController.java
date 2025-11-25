package com.app.payments.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.app.payments.application.service.PurchaseService;
import com.app.payments.domain.model.dto.PageResponse;
import com.app.payments.domain.model.dto.purchase.PurchaseCreateRequest;
import com.app.payments.domain.model.dto.purchase.PurchaseResponse;
import com.app.payments.domain.model.dto.purchase.PurchaseUpdateRequest;
import com.app.payments.domain.model.enums.PurchaseStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
@Tag(name = "Purchases", description = "Gestión de compras")
public class PurchaseController {

	@Autowired
	private PurchaseService purchaseService;

	@PostMapping
	@Operation(summary = "Crear una compra", description = "Crea una nueva compra asociada a un cliente.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de la compra a crear", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json", examples = {
			@io.swagger.v3.oas.annotations.media.ExampleObject(name = "Ejemplo crear compra", value = """
					{
					  "customerId": 1,
					  "description": "Televisión 55 pulgadas",
					  "totalAmount": 5000.00
					}
					""") })))
	public ResponseEntity<PurchaseResponse> create(@Validated @RequestBody PurchaseCreateRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(purchaseService.create(request));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Actualizar una compra", description = "Actualiza la descripción o el monto total de una compra, con validación contra lo ya pagado.")
	public PurchaseResponse update(@PathVariable Long id, @Validated @RequestBody PurchaseUpdateRequest request) {
		return purchaseService.update(id, request);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Obtener una compra por ID", description = "Devuelve el detalle de la compra, incluyendo montos pagados y restantes.")
	public PurchaseResponse get(@PathVariable Long id) {
		return purchaseService.getById(id);
	}

	@GetMapping
	@Operation(summary = "Listar compras", description = "Devuelve una lista paginada de compras, filtrando opcionalmente por cliente y estado.")
	public PageResponse<PurchaseResponse> list(@RequestParam(required = false) Long customerId,
			@RequestParam(required = false) PurchaseStatus status, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size, @RequestParam(defaultValue = "createdAt") String sort) {
		return purchaseService.list(customerId, status, page, size, sort);
	}

	@PostMapping("/{id}/cancel")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Cancelar una compra", description = "Marca una compra como cancelada. No se deben registrar nuevos pagos después de cancelarla.")
	public void cancel(@PathVariable Long id) {
		purchaseService.cancel(id);
	}
	


	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Eliminar una compra", description = "Elimina una compra si no tiene pagos asociados.")
	public void delete(@PathVariable Long id) {
		purchaseService.delete(id);
	}
}
