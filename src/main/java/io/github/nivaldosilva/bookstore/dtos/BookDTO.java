package io.github.nivaldosilva.bookstore.dtos;

import io.github.nivaldosilva.bookstore.domain.enums.Genre;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.ISBN;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDTO {

    private UUID id;

    @NotBlank(message = "ISBN cannot be empty")
    @ISBN(type = ISBN.Type.ANY, message = "Invalid ISBN format")
    @Size(max = 17, message = "ISBN cannot exceed 17 characters")
    private String isbn;

    @NotBlank(message = "Title cannot be empty")
    @Size(max = 200, message = "Title cannot exceed 200 characters")
    private String title;

    @NotBlank(message = "Synopsis cannot be empty")
    private String synopsis;

    @NotNull(message = "Genre cannot be null")
    private Genre genre;

    @NotNull(message = "Publication date cannot be null")
    @Past(message = "Publication date must be in the past")
    private LocalDate publicationDate;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.00", inclusive = false, message = "Price must be greater than zero")
    private BigDecimal price;

    @NotNull(message = "Stock quantity cannot be null")
    @PositiveOrZero(message = "Stock quantity cannot be negative")
    private Integer stockQuantity;

    @NotNull(message = "Author ID cannot be null")
    private UUID authorId;

    private String authorName;

}