package io.github.nivaldosilva.bookstore.utils.mappers;

import io.github.nivaldosilva.bookstore.domain.entities.Book;
import io.github.nivaldosilva.bookstore.domain.entities.Order;
import io.github.nivaldosilva.bookstore.domain.entities.OrderItem;
import io.github.nivaldosilva.bookstore.dtos.response.OrderItemResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderItemMapper {

    public static OrderItem toEntity(OrderItemResponse orderItemDTO, Order order, Book book) {
        return OrderItem.builder()
                .id(orderItemDTO.getId())
                .order(order)
                .book(book)
                .quantity(orderItemDTO.getQuantity())
                .unitPrice(orderItemDTO.getUnitPrice())
                .totalPrice(orderItemDTO.getTotalPrice())
                .build();
    }

    public static OrderItemResponse toDTO(OrderItem orderItem) {
        return OrderItemResponse.builder()
                .id(orderItem.getId())
                .bookId(orderItem.getBook() != null ? orderItem.getBook().getId() : null)
                .bookIsbn(orderItem.getBook() != null ? orderItem.getBook().getIsbn() : null)
                .bookTitle(orderItem.getBook() != null ? orderItem.getBook().getTitle() : null)
                .bookAuthorName(orderItem.getBook() != null && orderItem.getBook().getAuthor() != null
                        ? orderItem.getBook().getAuthor().getName()
                        : null)
                .quantity(orderItem.getQuantity())
                .unitPrice(orderItem.getUnitPrice())
                .totalPrice(orderItem.getTotalPrice())
                .build();
    }
}
