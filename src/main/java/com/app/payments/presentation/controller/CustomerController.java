package com.app.payments.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import com.app.payments.application.service.CustomerService;
import com.app.payments.application.service.PurchaseService;
import com.app.payments.domain.model.dto.PageResponse;
import com.app.payments.domain.model.dto.customer.CustomerCreateRequest;
import com.app.payments.domain.model.dto.customer.CustomerResponse;
import com.app.payments.domain.model.dto.customer.CustomerUpdateRequest;
import com.app.payments.domain.model.dto.purchase.PurchaseResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Tag(name = "Customers", description = "Gestión de clientes")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private PurchaseService purchaseService;

	@GetMapping("/r")
	public String validController() {
		return "Controller awake";
	}
	
	
	@PostMapping("/add")
	@Operation(
		    summary = "Crea un nuevo cliente",
		    description = "Registra un cliente con nombre, email, teléfono y notas opcionales.",
		    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
		        description = "Datos del cliente a crear",
		        content = @io.swagger.v3.oas.annotations.media.Content(
		            mediaType = "application/json",
		            examples = {
		                @io.swagger.v3.oas.annotations.media.ExampleObject(
		                    name = "Ejemplo crear cliente",
		                    value = """
		                    {
		                      "name": "Juan Pérez",
		                      "email": "juan.perez@example.com",
		                      "phone": "9991112233",
		                      "notes": "Cliente frecuente"
		                    }
		                    """
		                )
		            }
		        )
		    )
		)
	public ResponseEntity<?> create(@Validated @RequestBody CustomerCreateRequest request){
		CustomerResponse created = customerService.create(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}
	
//	@GetMapping
//	public ResponseEntity<?> getAll(){
//		List<CustomerResponse> lista = customerService.listAll();
//		return ResponseEntity.status(HttpStatus.ACCEPTED).body(lista);
//	}
//	
	@PutMapping("/{id}")
	@Operation(
		    summary = "Actualiza un cliente por su ID",
		    description = "Actualiza la información del cliente."
		)
	public CustomerResponse update(@PathVariable Long id, @Validated @RequestBody CustomerUpdateRequest request) {
		return customerService.update(id, request);
	}
	
	@GetMapping("/{id}")
	@Operation(
		    summary = "Obtiene un cliente por su ID",
		    description = "Devuelve la información completa del cliente."
		)
	public CustomerResponse get(@PathVariable Long id) {
	    return customerService.getById(id);
	}
	
	@GetMapping
	@Operation(
		    summary = "Lista clientes",
		    description = "Devuelve una lista paginada filtrada por nombre opcionalmente."
		)
    public PageResponse<CustomerResponse> list(
            @RequestParam(defaultValue = "") String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "name") String sort) {

        Page<CustomerResponse> p = customerService.list(q, page, size, sort);
        return PageResponse.<CustomerResponse>builder()
                .content(p.getContent())
                .page(p.getNumber())
                .size(p.getSize())
                .totalElements(p.getTotalElements())
                .totalPages(p.getTotalPages())
                .last(p.isLast())
                .build();
    }
	
	@GetMapping("/{id}/purchases")
	@Operation(
	        summary = "Listar compras de un cliente",
	        description = "Devuelve las compras del cliente indicado, de forma paginada."
	)
	public PageResponse<PurchaseResponse> listPurchasesByCustomer(
	        @PathVariable Long id,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "20") int size,
	        @RequestParam(defaultValue = "createdAt") String sort) {

	    // Reutilizamos la lógica de PurchaseService.list
	    return purchaseService.list(id, null, page, size, sort);
	}
	
	
	@DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(
		    summary = "Elimina un cliente",
		    description = "Elimina el cliente dado su ID."
		)
    public void delete(@PathVariable Long id) {
        customerService.delete(id);
    }
	
}
