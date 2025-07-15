package io.github.nivaldosilva.bookstore.service;

import io.github.nivaldosilva.bookstore.domain.entities.Author;
import io.github.nivaldosilva.bookstore.domain.entities.Book;
import io.github.nivaldosilva.bookstore.domain.repositories.AuthorRepository;
import io.github.nivaldosilva.bookstore.domain.repositories.BookRepository;
import io.github.nivaldosilva.bookstore.dtos.BookDTO;
import io.github.nivaldosilva.bookstore.exceptions.AuthorNotFoundException;
import io.github.nivaldosilva.bookstore.exceptions.BookNotFoundException;
import io.github.nivaldosilva.bookstore.exceptions.IsbnAlreadyExistsException;
import io.github.nivaldosilva.bookstore.mappers.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Transactional
    public BookDTO createBook(BookDTO bookDTO) {
        if (bookRepository.existsByIsbn(bookDTO.getIsbn())) {
            throw new IsbnAlreadyExistsException("ISBN '" + bookDTO.getIsbn() + "' já está registrado.");
        }
        Author author = authorRepository.findById(bookDTO.getAuthorId())
                .orElseThrow(() -> new AuthorNotFoundException("Autor não encontrado com ID: " + bookDTO.getAuthorId()));
        Book book = BookMapper.toEntity(bookDTO, author);
        Book savedBook = bookRepository.save(book);

        return BookMapper.toDTO(savedBook);
    }

    @Transactional(readOnly = true)
    public BookDTO findBookById(UUID id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Livro não encontrado com ID: " + id));

        return BookMapper.toDTO(book);
    }

    @Transactional(readOnly = true)
    public List<BookDTO> findAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public BookDTO updateBook(UUID id, BookDTO bookDTO) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Livro não encontrado com ID: " + id));

        if (!existingBook.getIsbn().equals(bookDTO.getIsbn()) && bookRepository.existsByIsbn(bookDTO.getIsbn())) {
            throw new IsbnAlreadyExistsException(
                    "ISBN '" + bookDTO.getIsbn() + "' já está registrado por outro livro.");
        }
        Author authorToUpdate = existingBook.getAuthor();
        if (bookDTO.getAuthorId() != null && !bookDTO.getAuthorId().equals(existingBook.getAuthor().getId())) {
            authorToUpdate = authorRepository.findById(bookDTO.getAuthorId())
                    .orElseThrow(
                            () -> new AuthorNotFoundException("Autor não encontrado com ID: " + bookDTO.getAuthorId()));
        }
        existingBook.setIsbn(bookDTO.getIsbn());
        existingBook.setTitle(bookDTO.getTitle());
        existingBook.setSynopsis(bookDTO.getSynopsis());
        existingBook.setGenre(bookDTO.getGenre());
        existingBook.setPublicationDate(bookDTO.getPublicationDate());
        existingBook.setPrice(bookDTO.getPrice());
        existingBook.setStockQuantity(bookDTO.getStockQuantity());
        existingBook.setAuthor(authorToUpdate);

        Book updatedBook = bookRepository.save(existingBook);
        return BookMapper.toDTO(updatedBook); 
    }

     @Transactional
    public void deleteBook(UUID id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Livro não encontrado com ID: " + id);
        }
        bookRepository.deleteById(id);
    }

}
