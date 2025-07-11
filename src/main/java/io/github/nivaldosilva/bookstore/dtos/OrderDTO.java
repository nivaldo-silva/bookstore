package io.github.nivaldosilva.bookstore.dtos;

import io.github.nivaldosilva.bookstore.domain.enums.OrderStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private UUID id;

    @NotNull(message = "Customer ID cannot be null")
    private UUID customerId;

    @NotNull(message = "Order items cannot be null")
    private List<OrderItemDTO> items; // For request and response: list of order items

    @NotNull(message = "Total amount cannot be null")
    @DecimalMin(value = "0.00", inclusive = true, message = "Total amount cannot be negative")
    private BigDecimal totalAmount;

    @NotNull(message = "Order status cannot be null")
    private OrderStatus status;

}
