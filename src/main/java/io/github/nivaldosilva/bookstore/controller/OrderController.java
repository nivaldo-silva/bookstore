package io.github.nivaldosilva.bookstore.controller;

import io.github.nivaldosilva.bookstore.service.OrderService;
import io.github.nivaldosilva.bookstore.dtos.ApiResponse;
import io.github.nivaldosilva.bookstore.dtos.OrderRequestDTO;
import io.github.nivaldosilva.bookstore.dtos.OrderResponseDTO;
import io.github.nivaldosilva.bookstore.domain.enums.OrderStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponseDTO>> createOrder(
            @Valid @RequestBody OrderRequestDTO orderRequestDTO) {
        logger.info("Iniciando criação de pedido para cliente email: {}", orderRequestDTO.getClientEmail());
        OrderResponseDTO createdOrder = orderService.createOrder(orderRequestDTO);
        ApiResponse<OrderResponseDTO> response = new ApiResponse<>("Pedido criado com sucesso!", createdOrder);
        logger.info("Pedido criado com sucesso.");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponseDTO>> getOrderById(@PathVariable UUID id) {
        logger.info("Iniciando busca de pedido por ID: {}", id);
        OrderResponseDTO orderResponseDTO = orderService.findOrderById(id);
        ApiResponse<OrderResponseDTO> response = new ApiResponse<>("Pedido encontrado com sucesso!", orderResponseDTO);
        logger.info("Pedido encontrado com sucesso.");
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponseDTO>>> getAllOrders() {
        logger.info("Iniciando busca por todos os pedidos.");
        List<OrderResponseDTO> orders = orderService.findAllOrders();
        ApiResponse<List<OrderResponseDTO>> response = new ApiResponse<>("Lista de pedidos recuperada com sucesso!",
                orders);
        logger.info("Busca por todos os pedidos concluída.");
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<OrderResponseDTO>> updateOrderStatus(@PathVariable UUID id,
            @RequestParam OrderStatus status) {
        logger.info("Iniciando atualização de status do pedido {} para: {}", id, status);
        OrderResponseDTO updatedOrderDTO = orderService.updateOrderStatus(id, status);
        ApiResponse<OrderResponseDTO> response = new ApiResponse<>(
                "Status do pedido atualizado com sucesso para: " + status.name(), updatedOrderDTO);
        logger.info("Status do pedido atualizado com sucesso.");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID id) {
        logger.info("Iniciando exclusão de pedido por ID: {}", id);
        orderService.deleteOrder(id);
        logger.info("Pedido excluído com sucesso.");
        return ResponseEntity.noContent().build();
    }
}
