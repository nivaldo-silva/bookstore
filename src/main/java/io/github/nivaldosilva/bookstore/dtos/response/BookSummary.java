package io.github.nivaldosilva.bookstore.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Resumo do livro")
public class BookSummary {

    @Schema(description = "ID único do livro")
    private UUID id;

    @Schema(description = "ISBN do livro")
    private String isbn;

    @Schema(description = "Título do livro")
    private String title;

    @Schema(description = "Nome do autor do livro")
    private String authorName;

}
