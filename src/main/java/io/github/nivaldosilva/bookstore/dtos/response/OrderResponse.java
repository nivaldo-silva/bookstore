package io.github.nivaldosilva.bookstore.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.nivaldosilva.bookstore.domain.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Resposta de pedido")
public class OrderResponse {

    @Schema(description = "ID único do pedido")
    private UUID id;

    @Schema(description = "Email do cliente")
    private String clientEmail;

    @Schema(description = "Nome completo do cliente")
    private String clientFullName;

    @Schema(description = "Itens do pedido")
    private List<OrderItemResponse> items;

    @Schema(description = "Preço total do pedido")
    private BigDecimal totalAmount;

    @Schema(description = "Status do pedido")
    private OrderStatus status;

}
