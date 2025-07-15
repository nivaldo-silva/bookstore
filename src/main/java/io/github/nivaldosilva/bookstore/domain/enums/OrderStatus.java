package io.github.nivaldosilva.bookstore.domain.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {

    PENDING,
    PROCESSING,
    SHIPPED,
    DELIVERED,
    CANCELLED,
    RETURNED
}