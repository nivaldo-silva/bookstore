package io.github.nivaldosilva.bookstore.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {

    private UUID id;

    @NotNull(message = "Order ID cannot be null")
    private UUID orderId;

    @NotNull(message = "Book ID cannot be null")
    private UUID bookId;

    @NotNull(message = "Quantity cannot be null")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;

    @NotNull(message = "Unit price cannot be null")
    @DecimalMin(value = "0.00", inclusive = false, message = "Unit price must be greater than zero")
    private BigDecimal unitPrice;

    @NotNull(message = "Total price cannot be null")
    @DecimalMin(value = "0.00", inclusive = false, message = "Total price must be greater than zero")
    private BigDecimal totalPrice;

}
