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
@Schema(description = "Resumo do autor")
public class AuthorSummary {

    @Schema(description = "ID único do autor")
    private UUID id;

    @Schema(description = "Nome do autor")
    private String name;

    @Schema(description = "Nacionalidade do autor")
    private String nationality;

}
