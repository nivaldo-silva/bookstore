package io.github.nivaldosilva.bookstore.controller;

import io.github.nivaldosilva.bookstore.domain.enums.Genre;
import io.github.nivaldosilva.bookstore.dtos.Common.PagedResponse;
import io.github.nivaldosilva.bookstore.dtos.request.CreateBookRequest;
import io.github.nivaldosilva.bookstore.dtos.response.BookResponse;
import io.github.nivaldosilva.bookstore.dtos.response.BookSummary;
import io.github.nivaldosilva.bookstore.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/books", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Livros", description = "Operações de gerenciamento de livros")
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;

    @Operation(summary = "Criar um novo livro")
    @ApiResponse(responseCode = "201", description = "Livro criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    @ApiResponse(responseCode = "409", description = "Livro com ISBN já existe")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResponse> createBook(
            @Parameter(description = "Dados do livro a ser criado", required = true)
            @Valid @RequestBody CreateBookRequest bookRequest) {
        logger.info("Iniciando criação de livro para ISBN: {}", bookRequest.getIsbn());
        BookResponse createdBook = bookService.createBook(bookRequest);
        logger.info("Livro criado com sucesso.");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }



    @Operation(summary = "Buscar livro por ID")
    @ApiResponse(responseCode = "200", description = "Livro encontrado")
    @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(
            @Parameter(description = "ID único do livro", required = true)
            @PathVariable UUID id) {
        logger.info("Iniciando busca de livro por ID: {}", id);
        BookResponse bookResponse = bookService.findBookById(id);
        logger.info("Livro encontrado com sucesso.");
        return ResponseEntity.ok(bookResponse);
    }



    @Operation(summary = "Listar livros")
    @ApiResponse(responseCode = "200", description = "Lista de livros recuperada com sucesso")
    @ApiResponse(responseCode = "400", description = "Parâmetros de filtro inválidos")
    @GetMapping
    public ResponseEntity<PagedResponse<BookSummary>> findAll(
            @Parameter(description = "Número da página (0-indexed)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página", example = "10") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Campo para ordenação", example = "title") @RequestParam(defaultValue = "title") String sortBy,
            @Parameter(description = "Direção da ordenação (ASC ou DESC)", example = "ASC") @RequestParam(defaultValue = "ASC") String sortDirection,
            @Parameter(description = "Filtro por gênero do livro", example = "FICTION") @RequestParam(required = false) Genre genre,
            @Parameter(description = "Filtro por ID do autor", example = "123e4567-e89b-12d3-a456-426614174000") @RequestParam(required = false) UUID authorId
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sortBy);
        Page<BookSummary> bookPage = bookService.findAllBooks(pageable, genre, authorId);

        PagedResponse<BookSummary> response = PagedResponse.<BookSummary>builder()
                .content(bookPage.getContent())
                .pageNumber(bookPage.getNumber())
                .pageSize(bookPage.getSize())
                .totalElements(bookPage.getTotalElements())
                .totalPages(bookPage.getTotalPages())
                .first(bookPage.isFirst())
                .last(bookPage.isLast())
                .build();
        return ResponseEntity.ok(response);
    }



    @Operation(summary = "Atualizar livro")
    @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "404", description = "Livro ou autor não encontrado")
    @ApiResponse(responseCode = "409", description = "Livro com ISBN já existe")
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(
            @Parameter(description = "ID único do livro", required = true)
            @PathVariable UUID id,
            @Parameter(description = "Dados atualizados do livro", required = true)
            @Valid @RequestBody CreateBookRequest bookRequest) {
        logger.info("Iniciando atualização de livro para ID: {}", id);
        BookResponse updatedBook = bookService.updateBook(id, bookRequest);
        logger.info("Livro atualizado com sucesso.");
        return ResponseEntity.ok(updatedBook);
    }



    @Operation(summary = "Excluir livro")
    @ApiResponse(responseCode = "204", description = "Livro excluído com sucesso")
    @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(
            @Parameter(description = "ID único do livro", required = true)
            @PathVariable UUID id) {
        logger.info("Iniciando exclusão de livro por ID: {}", id);
        bookService.deleteBook(id);
        logger.info("Livro excluído com sucesso.");
        return ResponseEntity.noContent().build();
    }
}
