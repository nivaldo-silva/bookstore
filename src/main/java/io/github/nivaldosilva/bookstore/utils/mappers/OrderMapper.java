package io.github.nivaldosilva.bookstore.utils.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import io.github.nivaldosilva.bookstore.domain.entities.Customer;
import io.github.nivaldosilva.bookstore.domain.entities.Order;
import io.github.nivaldosilva.bookstore.dtos.request.OrderRequest;
import io.github.nivaldosilva.bookstore.dtos.response.OrderItemResponse;
import io.github.nivaldosilva.bookstore.dtos.response.OrderResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderMapper {

        public static Order toEntity(OrderRequest orderRequestDTO, Customer customer) {
        return Order.builder()
                .customer(customer)
                .build();
    }
        public static OrderResponse toResponseDTO(Order order) {
        List<OrderItemResponse> itemDTOs = order.getItems() != null ? order.getItems().stream()
                .map(OrderItemMapper::toDTO)
                .collect(Collectors.toCollection(ArrayList::new)) : null;

        return OrderResponse.builder()
                .id(order.getId())
                .clientEmail(order.getCustomer() != null ? order.getCustomer().getEmail() : null) 
                .clientFullName(order.getCustomer() != null ? order.getCustomer().getFullName() : null) 
                .items(itemDTOs)
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .build();
    }
}
