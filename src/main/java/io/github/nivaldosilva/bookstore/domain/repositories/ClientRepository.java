package io.github.nivaldosilva.bookstore.domain.repositories;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import io.github.nivaldosilva.bookstore.domain.entities.Client;

public interface ClientRepository extends JpaRepository<Client, UUID> {

    boolean existsByEmail(String email);

    Optional<Client> findByEmail(String email);

}
