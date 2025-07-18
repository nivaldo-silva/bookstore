package io.github.nivaldosilva.bookstore.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.ISBN;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Item de pedido")
public class OrderItemRequest {

    @Schema(description = "ISBN do livro")
    @NotBlank(message = "Book ISBN cannot be empty")
    @ISBN(type = ISBN.Type.ANY, message = "Invalid ISBN format")
    private String bookIsbn;

    @Schema(description = "Título do livro")
    @NotBlank(message = "Book title cannot be empty")
    private String bookTitle;

    @Schema(description = "Quantidade do livro")
    @NotNull(message = "Quantity cannot be null")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;
}
