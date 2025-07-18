package io.github.nivaldosilva.bookstore.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Detalhes de um item de pedido")
public class OrderItemResponse {

    @Schema(description = "ID único do item de pedido")
    private UUID id;

    @Schema(description = "ID único do livro")
    private UUID bookId;

    @Schema(description = "ISBN do livro")
    private String bookIsbn;

    @Schema(description = "Título do livro")
    private String bookTitle;

    @Schema(description = "Nome do autor do livro")
    private String bookAuthorName;

    @Schema(description = "Quantidade do livro")
    private Integer quantity;

    @Schema(description = "Preço unitário do livro")
    private BigDecimal unitPrice;

    @Schema(description = "Preço total do livro")
    private BigDecimal totalPrice;

}
