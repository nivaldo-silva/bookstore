package io.github.nivaldosilva.bookstore.controller;

import io.github.nivaldosilva.bookstore.dtos.ApiResponse;
import io.github.nivaldosilva.bookstore.dtos.BookDTO;
import io.github.nivaldosilva.bookstore.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<ApiResponse<BookDTO>> createBook(@Valid @RequestBody BookDTO bookDTO) {

        logger.info("Iniciando criação de livro para ISBN: {}", bookDTO.getIsbn());
        BookDTO createdBook = bookService.createBook(bookDTO);
        ApiResponse<BookDTO> response = new ApiResponse<>("Livro criado com sucesso!", createdBook);
        logger.info("Livro criado com sucesso.");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookDTO>> getBookById(@PathVariable UUID id) {

        logger.info("Iniciando busca de livro por ID: {}", id);
        BookDTO bookDTO = bookService.findBookById(id);
        ApiResponse<BookDTO> response = new ApiResponse<>("Livro encontrado com sucesso!", bookDTO);
        logger.info("Livro encontrado com sucesso.");
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BookDTO>>> getAllBooks() {

        logger.info("Iniciando busca por todos os livros.");
        List<BookDTO> books = bookService.findAllBooks();
        ApiResponse<List<BookDTO>> response = new ApiResponse<>("Lista de livros recuperada com sucesso!", books);
        logger.info("Busca por todos os livros concluída.");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BookDTO>> updateBook(@PathVariable UUID id, @Valid @RequestBody BookDTO bookDTO) {
        logger.info("Iniciando atualização de livro para ID: {}", id);
        BookDTO updatedBookDTO = bookService.updateBook(id, bookDTO);
        ApiResponse<BookDTO> response = new ApiResponse<>("Livro atualizado com sucesso!", updatedBookDTO);
        logger.info("Livro atualizado com sucesso.");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable UUID id) {
        logger.info("Iniciando exclusão de livro por ID: {}", id);
        bookService.deleteBook(id);
        logger.info("Livro excluído com sucesso.");
        return ResponseEntity.noContent().build();
    }

}
