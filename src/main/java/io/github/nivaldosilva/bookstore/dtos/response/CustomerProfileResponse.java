package io.github.nivaldosilva.bookstore.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Perfil completo do cliente")
public class CustomerProfileResponse {

    @Schema(description = "ID único do cliente")
    private UUID id;

    @Schema(description = "Nome completo do cliente")
    private String fullName;

    @Schema(description = "Email do cliente")
    private String email;

    @Schema(description = "Lista de livros comprados pelo cliente")
    private List<BookSummary> purchasedBooks;

    @Schema(description = "Lista de pedidos do cliente")
    private List<OrderResponse> clientOrders;

}
