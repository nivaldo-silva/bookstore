package io.github.nivaldosilva.bookstore.mappers;

import io.github.nivaldosilva.bookstore.domain.entities.Book;
import io.github.nivaldosilva.bookstore.domain.entities.Order;
import io.github.nivaldosilva.bookstore.domain.entities.OrderItem;
import io.github.nivaldosilva.bookstore.dtos.OrderItemDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderItemMapper {

    public static OrderItem toEntity(OrderItemDTO orderItemDTO, Order order, Book book) {
        return OrderItem.builder()
                .id(orderItemDTO.getId())
                .order(order)
                .book(book)
                .quantity(orderItemDTO.getQuantity())
                .unitPrice(orderItemDTO.getUnitPrice())
                .totalPrice(orderItemDTO.getTotalPrice())
                .build();
    }

    public static OrderItemDTO toDTO(OrderItem orderItem) {
        return OrderItemDTO.builder()
                .id(orderItem.getId())
                .orderId(orderItem.getOrder() != null ? orderItem.getOrder().getId() : null)
                .bookId(orderItem.getBook() != null ? orderItem.getBook().getId() : null)
                .quantity(orderItem.getQuantity())
                .unitPrice(orderItem.getUnitPrice())
                .totalPrice(orderItem.getTotalPrice())
                .build();
    }

}
