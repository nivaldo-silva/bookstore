package io.github.nivaldosilva.bookstore.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Detalhes completos de um autor")
public class AuthorDetailsResponse {

    @Schema(description = "ID único do autor")
    private UUID id;

    @Schema(description = "Nome do autor")
    private String name;

    @Schema(description = "Nacionalidade do autor")
    private String nationality;

    @Schema(description = "Data de nascimento do autor")
    private LocalDate birthDate;

    @Schema(description = "Biografia do autor")
    private String biography;
    
    private List<BookSummary> books;

}
