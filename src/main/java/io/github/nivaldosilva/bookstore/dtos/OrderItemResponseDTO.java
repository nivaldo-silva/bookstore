package io.github.nivaldosilva.bookstore.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderItemResponseDTO {

    private UUID id;
    private UUID bookId;
    private String bookIsbn;
    private String bookTitle;
    private String bookAuthorName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;

}
