package io.github.nivaldosilva.bookstore.controller;

import io.github.nivaldosilva.bookstore.dtos.request.CreateAuthorRequest;
import io.github.nivaldosilva.bookstore.dtos.response.AuthorDetailsResponse;
import io.github.nivaldosilva.bookstore.dtos.response.AuthorSummary;
import io.github.nivaldosilva.bookstore.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/authors", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Autores", description = "Operações relacionadas ao gerenciamento de autores de livros")
public class AuthorController {

    private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);
    private final AuthorService authorService;

    @Operation(summary = "Criar um novo autor")
    @ApiResponse(responseCode = "201", description = "Autor criado com sucesso")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorDetailsResponse> createAuthor(
            @Parameter(description = "Dados do autor a ser criado", required = true)
            @Valid @RequestBody CreateAuthorRequest authorRequest) {
        logger.info("Iniciando criação de autor para nome: {}", authorRequest.getName());
        AuthorDetailsResponse createdAuthor = authorService.createAuthor(authorRequest);
        logger.info("Autor criado com sucesso.");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAuthor);
    }



    @Operation(summary = "Buscar autor por ID")
    @ApiResponse(responseCode = "200", description = "Autor encontrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<AuthorDetailsResponse> getAuthorById(
            @Parameter(description = "ID único do autor", required = true)
            @PathVariable UUID id) {
        logger.info("Iniciando busca de autor por ID: {}", id);
        AuthorDetailsResponse authorResponse = authorService.findAuthorById(id);
        logger.info("Autor encontrado com sucesso.");
        return ResponseEntity.ok(authorResponse);
    }



    @Operation(summary = "Listar todos os autores")
    @ApiResponse(responseCode = "200", description = "Lista de autores recuperada com sucesso")
    @GetMapping
    public ResponseEntity<List<AuthorSummary>> getAllAuthors() {
        logger.info("Iniciando busca por todos os autores.");
        List<AuthorSummary> authors = authorService.findAllAuthors();
        logger.info("Busca por todos os autores concluída.");
        return ResponseEntity.ok(authors);
    }



    @Operation(summary = "Atualizar autor")
    @ApiResponse(responseCode = "200", description = "Autor atualizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    @ApiResponse(responseCode = "409", description = "Autor com o nome já existe")
    @PutMapping("/{id}")
    public ResponseEntity<AuthorDetailsResponse> updateAuthor(
            @Parameter(description = "ID único do autor", required = true) @PathVariable UUID id,
            @Parameter(description = "Dados atualizados do autor", required = true) @Valid @RequestBody CreateAuthorRequest authorRequest) {
        logger.info("Iniciando atualização de autor para ID: {}", id);
        AuthorDetailsResponse updatedAuthor = authorService.updateAuthor(id, authorRequest);
        logger.info("Autor atualizado com sucesso.");
        return ResponseEntity.ok(updatedAuthor);
    }

    

    @Operation(summary = "Excluir autor")
    @ApiResponse(responseCode = "204", description = "Autor excluído com sucesso")
    @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(
            @Parameter(description = "ID único do autor", required = true) @PathVariable UUID id) {
        logger.info("Iniciando exclusão de autor por ID: {}", id);
        authorService.deleteAuthor(id);
        logger.info("Autor excluído com sucesso.");
        return ResponseEntity.noContent().build();
    }
}
