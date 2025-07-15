package io.github.nivaldosilva.bookstore.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.nivaldosilva.bookstore.domain.enums.OrderStatus;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponseDTO {

    private UUID id;
    private String clientEmail;
    private String clientFullName;
    private List<OrderItemResponseDTO> items;
    private BigDecimal totalAmount;
    private OrderStatus status;

}
