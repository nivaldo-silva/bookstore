package io.github.nivaldosilva.bookstore.mappers;

import io.github.nivaldosilva.bookstore.domain.entities.Client;
import io.github.nivaldosilva.bookstore.domain.entities.Order;
import io.github.nivaldosilva.bookstore.dtos.OrderRequestDTO;
import io.github.nivaldosilva.bookstore.dtos.OrderResponseDTO;
import io.github.nivaldosilva.bookstore.dtos.OrderItemResponseDTO;
import lombok.experimental.UtilityClass;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class OrderMapper {

        public static Order toEntity(OrderRequestDTO orderRequestDTO, Client client) {
                return Order.builder()
                                .client(client)
                                .build();
        }

        public static OrderResponseDTO toResponseDTO(Order order) {
                List<OrderItemResponseDTO> itemDTOs = order.getItems() != null ? order.getItems().stream()
                                .map(OrderItemMapper::toDTO)
                                .collect(Collectors.toList()) : null;

                return OrderResponseDTO.builder()
                                .id(order.getId())
                                .clientEmail(order.getClient() != null ? order.getClient().getEmail() : null)
                                .clientFullName(order.getClient() != null ? order.getClient().getFullName() : null)
                                .items(itemDTOs)
                                .totalAmount(order.getTotalAmount())
                                .status(order.getStatus())
                                .build();
        }
}
