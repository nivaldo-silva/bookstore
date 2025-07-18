package io.github.nivaldosilva.bookstore.utils.mappers;

import java.util.List;
import java.util.stream.Collectors;
import io.github.nivaldosilva.bookstore.domain.entities.Customer;
import io.github.nivaldosilva.bookstore.dtos.request.CustomerRegistrationRequest;
import io.github.nivaldosilva.bookstore.dtos.response.CustomerProfileResponse;
import io.github.nivaldosilva.bookstore.dtos.response.OrderResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CustomerMapper {

    public static Customer toEntity(CustomerRegistrationRequest request) {
        return Customer.builder()
                .id(request.getId())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
    }

    public static CustomerProfileResponse toProfileResponseDTO(Customer customer) {
    List<OrderResponse> orders = customer.getOrders() != null ? customer.getOrders().stream()
            .map(OrderMapper::toResponseDTO) 
            .collect(Collectors.toList()) : null;

    return CustomerProfileResponse.builder()
            .id(customer.getId())
            .fullName(customer.getFullName())
            .email(customer.getEmail())
            .clientOrders(orders)
            .build();
}

     public static CustomerRegistrationRequest toRegistrationDTO(Customer customer) {
        return CustomerRegistrationRequest.builder()
                .id(customer.getId())
                .fullName(customer.getFullName())
                .email(customer.getEmail())
                .build();
    }

}
