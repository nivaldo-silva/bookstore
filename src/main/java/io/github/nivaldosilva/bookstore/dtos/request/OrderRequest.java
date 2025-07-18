package io.github.nivaldosilva.bookstore.dtos.request;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Pedido de compra")
public class OrderRequest {

    @Schema(description = "ID único do pedido")
    private UUID id;

    @Schema(description = "Email do cliente")
    @NotBlank(message = "Client email cannot be empty")
    @Email(message = "Invalid client email format")
    private String customerEmail;


    @Schema(description = "Itens do pedido")
    @NotNull(message = "Order items cannot be null")
    private List<OrderItemRequest> items;
}
