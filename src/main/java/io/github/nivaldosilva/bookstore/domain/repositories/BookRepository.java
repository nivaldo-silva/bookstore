package io.github.nivaldosilva.bookstore.domain.repositories;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import io.github.nivaldosilva.bookstore.domain.entities.Book;

public interface BookRepository extends JpaRepository<Book, UUID> {

    boolean existsByIsbn(String isbn);

    Optional<Book> findByIsbn(String isbn);

}
