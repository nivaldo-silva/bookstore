package io.github.nivaldosilva.bookstore.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class OrderRequestDTO {

    private UUID id;

    @NotBlank(message = "Client email cannot be empty")
    @Email(message = "Invalid client email format")
    private String clientEmail;

    @NotNull(message = "Order items cannot be null")
    private List<OrderItemRequestDTO> items;
}
