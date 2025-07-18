package io.github.nivaldosilva.bookstore.domain.repositories;

import io.github.nivaldosilva.bookstore.domain.entities.Book;
import io.github.nivaldosilva.bookstore.domain.enums.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {

    boolean existsByIsbn(String isbn);

    Optional<Book> findByIsbn(String isbn);

    Page<Book> findByGenre(Genre genre, Pageable pageable);

    Page<Book> findByAuthorId(UUID authorId, Pageable pageable);

    Page<Book> findByGenreAndAuthorId(Genre genre, UUID authorId, Pageable pageable);

}
