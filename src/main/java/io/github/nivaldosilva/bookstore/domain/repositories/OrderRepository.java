package io.github.nivaldosilva.bookstore.domain.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import io.github.nivaldosilva.bookstore.domain.entities.Order;

public interface OrderRepository extends JpaRepository<Order, UUID> {

}
