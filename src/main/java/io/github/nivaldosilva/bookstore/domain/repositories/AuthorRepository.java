package io.github.nivaldosilva.bookstore.domain.repositories;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import io.github.nivaldosilva.bookstore.domain.entities.Author;

public interface AuthorRepository extends JpaRepository<Author, UUID> {

    Optional<Author> findByName(String name);

    boolean existsByName(String name);

}
