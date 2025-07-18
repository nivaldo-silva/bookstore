package io.github.nivaldosilva.bookstore.dtos.Common;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Resposta paginada genérica da API")
public class PagedResponse<T> {

    @Schema(description = "Conteúdo da página atual")
    private List<T> content;

    @Schema(description = "Número da página atual (baseado em zero)")
    private int pageNumber;

    @Schema(description = "Tamanho da página")
    private int pageSize;

    @Schema(description = "Número total de elementos em todas as páginas")
    private long totalElements;

    @Schema(description = "Número total de páginas")
    private int totalPages;

    @Schema(description = "Indica se é a primeira página")
    private boolean first;

    @Schema(description = "Indica se é a última página")
    private boolean last;

}
