package io.github.nivaldosilva.bookstore.mappers;

import io.github.nivaldosilva.bookstore.domain.entities.Order;
import io.github.nivaldosilva.bookstore.domain.entities.User;
import io.github.nivaldosilva.bookstore.dtos.OrderDTO;
import io.github.nivaldosilva.bookstore.dtos.OrderItemDTO;
import lombok.experimental.UtilityClass;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class OrderMapper {

    public static Order toEntity(OrderDTO orderDTO, User customer) {
        return Order.builder()
                .id(orderDTO.getId()) 
                .customer(customer) 
                .totalAmount(orderDTO.getTotalAmount())
                .status(orderDTO.getStatus())                
                .build();
    }

     public static OrderDTO toDTO(Order order) {
        List<OrderItemDTO> itemDTOs = order.getItems() != null ?
                order.getItems().stream()
                        .map(OrderItemMapper::toDTO) 
                        .collect(Collectors.toList()) :
                null;

        return OrderDTO.builder()
                .id(order.getId())
                .customerId(order.getCustomer() != null ? order.getCustomer().getId() : null) 
                .items(itemDTOs)
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())              
                .build();
    }

}
