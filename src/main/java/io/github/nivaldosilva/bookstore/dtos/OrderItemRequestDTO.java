package io.github.nivaldosilva.bookstore.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.ISBN;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequestDTO {

    @NotBlank(message = "Book ISBN cannot be empty")
    @ISBN(type = ISBN.Type.ANY, message = "Invalid ISBN format")
    private String bookIsbn;

    private String bookTitle;

    @NotNull(message = "Quantity cannot be null")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;
}
