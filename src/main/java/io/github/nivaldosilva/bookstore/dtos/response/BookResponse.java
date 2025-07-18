package io.github.nivaldosilva.bookstore.dtos.response;

import io.github.nivaldosilva.bookstore.domain.enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Resposta detalhada de um livro")
public class BookResponse {

    @Schema(description = "ID único do livro")
    private UUID id;

    @Schema(description = "ISBN do livro")
    private String isbn;

    @Schema(description = "Título do livro")
    private String title;

    @Schema(description = "Sinopse do livro")
    private String synopsis;

    @Schema(description = "Gênero do livro")
    private Genre genre;

    @Schema(description = "Data de publicação do livro")
    private LocalDate publicationDate;

    @Schema(description = "Preço do livro")
    private BigDecimal price;

    @Schema(description = "Quantidade em estoque do livro")
    private Integer stockQuantity;

    @Schema(description = "ID do autor do livro")
    private UUID authorId;

    @Schema(description = "Nome do autor do livro")
    private String authorName;

}
