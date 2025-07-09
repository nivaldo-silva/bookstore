package io.github.nivaldosilva.bookstore.domain.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import io.github.nivaldosilva.bookstore.domain.entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {

}
