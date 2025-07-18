package io.github.nivaldosilva.bookstore.dtos.Common;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Resposta padrão para erros da API")
public class ErrorResponse {

    @Schema(description = "Timestamp da ocorrência do erro", example = "2023-07-18T10:30:00")
    private LocalDateTime timestamp;

    @Schema(description = "Código de status HTTP", example = "400")
    private int status;

    @Schema(description = "Tipo de erro HTTP", example = "Bad Request")
    private String error;

    @Schema(description = "Mensagem detalhada do erro", example = "Dados de entrada inválidos.")
    private String message;

    @Schema(description = "Caminho da requisição que gerou o erro", example = "/api/v1/books")
    private String path;

    @Schema(description = "Detalhes específicos de validação de campos")   
    private Map<String, String> details;
}
