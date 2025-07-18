package io.github.nivaldosilva.bookstore.controller;

import io.github.nivaldosilva.bookstore.dtos.request.CustomerRegistrationRequest;
import io.github.nivaldosilva.bookstore.dtos.request.PasswordUpdate;
import io.github.nivaldosilva.bookstore.dtos.response.CustomerProfileResponse;
import io.github.nivaldosilva.bookstore.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/customers", produces = MediaType.APPLICATION_JSON_VALUE) 
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "Operações relacionadas ao gerenciamento de clientes")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerService customerService;

    @Operation(summary = "Registrar um novo cliente")
    @ApiResponse(responseCode = "201", description = "Cliente registrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    @ApiResponse(responseCode = "409", description = "Cliente com o e-mail já existe")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerProfileResponse> registerCustomer( 
            @Parameter(description = "Dados do cliente a ser registrado", required = true)
            @Valid @RequestBody CustomerRegistrationRequest customerRequest) { 
        logger.info("Iniciando registro de cliente para email: {}", customerRequest.getEmail());
        CustomerProfileResponse registeredCustomer = customerService.registerCustomer(customerRequest); 
        logger.info("Cliente registrado com sucesso.");
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredCustomer);
    }


    @Operation(summary = "Buscar cliente por ID")
    @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerProfileResponse> getCustomerById(
            @Parameter(description = "ID único do cliente", required = true)
            @PathVariable("id") UUID id) {
        logger.info("Iniciando busca de cliente por ID: {}", id);
        CustomerProfileResponse customerResponse = customerService.findCustomerById(id);
        logger.info("Cliente encontrado com sucesso.");
        return ResponseEntity.ok(customerResponse);
    }


    @Operation(summary = "Listar todos os clientes")
    @ApiResponse(responseCode = "200", description = "Lista de clientes recuperada com sucesso")
    @GetMapping
    public ResponseEntity<List<CustomerProfileResponse>> getAllCustomers() { 
        logger.info("Iniciando busca por todos os clientes.");
        List<CustomerProfileResponse> customers = customerService.findAllCustomers();
        logger.info("Busca por todos os clientes concluída.");
        return ResponseEntity.ok(customers);
    }


    @Operation(summary = "Atualizar cliente")
    @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    @ApiResponse(responseCode = "409", description = "Cliente com o e-mail já existe")
    @PutMapping("/{id}")
    public ResponseEntity<CustomerProfileResponse> updateCustomer(
            @Parameter(description = "ID único do cliente", required = true) @PathVariable("id") UUID id,
            @Parameter(description = "Dados atualizados do cliente", required = true) @Valid @RequestBody CustomerRegistrationRequest customerRequest) {
        logger.info("Iniciando atualização de cliente para ID: {}", id);
        CustomerProfileResponse updatedCustomer = customerService.updateCustomer(id, customerRequest);
        logger.info("Cliente atualizado com sucesso.");
        return ResponseEntity.ok(updatedCustomer);
    }


    @Operation(summary = "Excluir cliente")
    @ApiResponse(responseCode = "204", description = "Cliente excluído com sucesso")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(
            @Parameter(description = "ID único do cliente", required = true) @PathVariable("id") UUID id) {
        logger.info("Iniciando exclusão de cliente por ID: {}", id);
        customerService.deleteCustomer(id);
        logger.info("Cliente excluído com sucesso.");
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Atualizar senha do cliente")
    @ApiResponse(responseCode = "200", description = "Senha atualizada com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    @PatchMapping("/{id}/password")
    public ResponseEntity<CustomerProfileResponse> updatePassword(
            @Parameter(description = "ID único do cliente", required = true) @PathVariable("id") UUID id,
            @Parameter(description = "Nova senha do cliente", required = true) @Valid @RequestBody PasswordUpdate passwordUpdate) {
        logger.info("Iniciando atualização de senha para cliente com ID: {}", id);
        CustomerProfileResponse updatedCustomer = customerService.updatePassword(id, passwordUpdate.getNewPassword());
        logger.info("Senha atualizada com sucesso.");
        return ResponseEntity.ok(updatedCustomer);
    }
}
