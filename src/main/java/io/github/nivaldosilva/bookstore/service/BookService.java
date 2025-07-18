package io.github.nivaldosilva.bookstore.service;

import io.github.nivaldosilva.bookstore.domain.entities.Author;
import io.github.nivaldosilva.bookstore.domain.entities.Book;
import io.github.nivaldosilva.bookstore.domain.enums.Genre;
import io.github.nivaldosilva.bookstore.domain.repositories.AuthorRepository;
import io.github.nivaldosilva.bookstore.domain.repositories.BookRepository;
import io.github.nivaldosilva.bookstore.dtos.request.CreateBookRequest;
import io.github.nivaldosilva.bookstore.dtos.response.BookResponse;
import io.github.nivaldosilva.bookstore.dtos.response.BookSummary;
import io.github.nivaldosilva.bookstore.exceptions.AuthorNotFoundException;
import io.github.nivaldosilva.bookstore.exceptions.BookNotFoundException;
import io.github.nivaldosilva.bookstore.exceptions.IsbnAlreadyExistsException;
import io.github.nivaldosilva.bookstore.utils.mappers.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Transactional
    public BookResponse createBook(CreateBookRequest request) {
        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new IsbnAlreadyExistsException("ISBN '" + request.getIsbn() + "' já está registrado.");
        }
        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new AuthorNotFoundException("Autor não encontrado com ID: " + request.getAuthorId()));
        Book book = BookMapper.toEntity(request, author);
        Book savedBook = bookRepository.save(book);

        return BookMapper.toResponseDTO(savedBook);
    }

    @Transactional(readOnly = true)
    public BookResponse findBookById(UUID id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Livro não encontrado com ID: " + id));

        return BookMapper.toResponseDTO(book);
    }

    @Transactional(readOnly = true)
    public Page<BookSummary> findAllBooks(Pageable pageable, Genre genre, UUID authorId) {
        Page<Book> bookPage;
        if (genre != null && authorId != null) {
            bookPage = bookRepository.findByGenreAndAuthorId(genre, authorId, pageable);
        } else if (genre != null) {
            bookPage = bookRepository.findByGenre(genre, pageable);
        } else if (authorId != null) {
            bookPage = bookRepository.findByAuthorId(authorId, pageable);
        } else {
            bookPage = bookRepository.findAll(pageable);
        }
        return bookPage.map(BookMapper::toSummaryDTO);
    }

    @Transactional
    public BookResponse updateBook(UUID id, CreateBookRequest request) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Livro não encontrado com ID: " + id));

        if (!existingBook.getIsbn().equals(request.getIsbn()) && bookRepository.existsByIsbn(request.getIsbn())) {
            throw new IsbnAlreadyExistsException(
                    "ISBN '" + request.getIsbn() + "' já está registrado por outro livro.");
        }
        Author authorToUpdate = existingBook.getAuthor();
        if (request.getAuthorId() != null && !request.getAuthorId().equals(existingBook.getAuthor().getId())) {
            authorToUpdate = authorRepository.findById(request.getAuthorId())
                    .orElseThrow(
                            () -> new AuthorNotFoundException("Autor não encontrado com ID: " + request.getAuthorId()));
        }
        existingBook.setIsbn(request.getIsbn());
        existingBook.setTitle(request.getTitle());
        existingBook.setSynopsis(request.getSynopsis());
        existingBook.setGenre(request.getGenre());
        existingBook.setPublicationDate(request.getPublicationDate());
        existingBook.setPrice(request.getPrice());
        existingBook.setStockQuantity(request.getStockQuantity());
        existingBook.setAuthor(authorToUpdate);

        Book updatedBook = bookRepository.save(existingBook);
        return BookMapper.toResponseDTO(updatedBook);
    }

    @Transactional
    public void deleteBook(UUID id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Livro não encontrado com ID: " + id);
        }
        bookRepository.deleteById(id);
    }
}
