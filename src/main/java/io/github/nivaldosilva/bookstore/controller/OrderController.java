package io.github.nivaldosilva.bookstore.controller;

import io.github.nivaldosilva.bookstore.domain.enums.OrderStatus;
import io.github.nivaldosilva.bookstore.dtos.request.OrderRequest;
import io.github.nivaldosilva.bookstore.dtos.response.OrderResponse;
import io.github.nivaldosilva.bookstore.service.OrderService;
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
@RequestMapping(value = "/api/v1/orders", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Pedidos", description = "Operações relacionadas ao gerenciamento de pedidos de livros")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;

    @Operation(summary = "Criar um novo pedido")
    @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos ou estoque insuficiente")
    @ApiResponse(responseCode = "404", description = "Cliente ou livro(s) não encontrado(s)")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponse> createOrder(
            @Parameter(description = "Dados do pedido a ser criado", required = true)
            @Valid @RequestBody OrderRequest orderRequest) {
        logger.info("Iniciando criação de pedido para cliente email: {}", orderRequest.getCustomerEmail());
        OrderResponse createdOrder = orderService.createOrder(orderRequest);
        logger.info("Pedido criado com sucesso.");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }


    @Operation(summary = "Buscar pedido por ID")
    @ApiResponse(responseCode = "200", description = "Pedido encontrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(
            @Parameter(description = "ID único do pedido", required = true)
            @PathVariable UUID id) {
        logger.info("Iniciando busca de pedido por ID: {}", id);
        OrderResponse orderResponse = orderService.findOrderById(id);
        logger.info("Pedido encontrado com sucesso.");
        return ResponseEntity.ok(orderResponse);
    }


    @Operation(summary = "Listar todos os pedidos")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos recuperada com sucesso")
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        logger.info("Iniciando busca por todos os pedidos.");
        List<OrderResponse> orders = orderService.findAllOrders();
        logger.info("Busca por todos os pedidos concluída.");
        return ResponseEntity.ok(orders);
    }


    @Operation(summary = "Atualizar status do pedido")
    @ApiResponse(responseCode = "200", description = "Status do pedido atualizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Status inválido ou dados fornecidos")
    @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @Parameter(description = "ID único do pedido", required = true) @PathVariable UUID id,
            @Parameter(description = "Novo status do pedido", required = true, example = "SHIPPED") @RequestParam OrderStatus status) {
        logger.info("Iniciando atualização de status do pedido {} para: {}", id, status);
        OrderResponse updatedOrder = orderService.updateOrderStatus(id, status);
        logger.info("Status do pedido atualizado com sucesso.");
        return ResponseEntity.ok(updatedOrder);
    }
    

    @Operation(summary = "Excluir pedido")
    @ApiResponse(responseCode = "204", description = "Pedido excluído com sucesso")
    @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(
            @Parameter(description = "ID único do pedido", required = true) @PathVariable UUID id) {
        logger.info("Iniciando exclusão de pedido por ID: {}", id);
        orderService.deleteOrder(id);
        logger.info("Pedido excluído com sucesso.");
        return ResponseEntity.noContent().build();
    }
}
