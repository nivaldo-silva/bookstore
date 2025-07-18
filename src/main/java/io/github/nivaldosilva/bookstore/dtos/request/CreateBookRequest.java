package io.github.nivaldosilva.bookstore.dtos.request;

import io.github.nivaldosilva.bookstore.domain.enums.Genre;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Dados do livro")
public class CreateBookRequest {

    @Schema(description = "ID único do livro")
    private UUID id;

    @Schema(description = "ISBN do livro")
    @NotBlank(message = "ISBN cannot be empty")
    @ISBN(type = ISBN.Type.ANY, message = "Invalid ISBN format")
    @Size(max = 17, message = "ISBN cannot exceed 17 characters")
    private String isbn;

    @Schema(description = "Título do livro")
    @NotBlank(message = "Title cannot be empty")
    @Size(max = 200, message = "Title cannot exceed 200 characters")
    private String title;

    @Schema(description = "Sinopse do livro")
    @NotBlank(message = "Synopsis cannot be empty")
    private String synopsis;

    @Schema(description = "Gênero do livro")
    @NotNull(message = "Genre cannot be null")
    private Genre genre;

    @Schema(description = "Data de publicação do livro")
    @NotNull(message = "Publication date cannot be null")
    @Past(message = "Publication date must be in the past")
    private LocalDate publicationDate;

    @Schema(description = "Preço do livro")
    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.00", inclusive = false, message = "Price must be greater than zero")
    private BigDecimal price;

    @Schema(description = "Quantidade em estoque do livro")
    @NotNull(message = "Stock quantity cannot be null")
    @PositiveOrZero(message = "Stock quantity cannot be negative")
    private Integer stockQuantity;

    @Schema(description = "ID do autor do livro")
    @NotNull(message = "Author ID cannot be null")
    private UUID authorId;

    @Schema(description = "Nome do autor do livro")
    private String authorName;

}