package io.github.nivaldosilva.bookstore.domain.repositories;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import io.github.nivaldosilva.bookstore.domain.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    boolean existsByEmail(String email);

    Optional<Customer> findByEmail(String email);

}
