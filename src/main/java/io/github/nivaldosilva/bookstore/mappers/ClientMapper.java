package io.github.nivaldosilva.bookstore.mappers;

import java.util.List;
import java.util.stream.Collectors;
import io.github.nivaldosilva.bookstore.domain.entities.Client;
import io.github.nivaldosilva.bookstore.dtos.BookDTO;
import io.github.nivaldosilva.bookstore.dtos.ClientDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ClientMapper {

    public static Client toEntity(ClientDTO clientDTO) {
        return Client.builder()
                .id(clientDTO.getId())
                .fullName(clientDTO.getFullName())
                .email(clientDTO.getEmail())
                .password(clientDTO.getPassword())
                .build();
    }

    public static ClientDTO toDTO(Client client) {
        List<BookDTO> purchasedBooks = client.getOrders() != null ? client.getOrders().stream()
                .flatMap(order -> order.getItems().stream())
                .map(orderItem -> BookMapper.toDTO(orderItem.getBook())) 
                .distinct() 
                .collect(Collectors.toList()) : null;

        return ClientDTO.builder()
                .id(client.getId())
                .fullName(client.getFullName())
                .email(client.getEmail())
                .purchasedBooks(purchasedBooks) 
                .build();
    }

}
