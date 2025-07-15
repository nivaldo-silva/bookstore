package io.github.nivaldosilva.bookstore.controller;

import io.github.nivaldosilva.bookstore.dtos.ApiResponse;
import io.github.nivaldosilva.bookstore.dtos.AuthorDTO;
import io.github.nivaldosilva.bookstore.service.AuthorService;
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
@RequestMapping("/api/v1/authors")
@RequiredArgsConstructor
public class AuthorController {

    private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);
    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<ApiResponse<AuthorDTO>> createAuthor(@Valid @RequestBody AuthorDTO authorDTO) {      
        logger.info("Iniciando criação de autor para nome: {}", authorDTO.getName());
        AuthorDTO createdAuthor = authorService.createAuthor(authorDTO);
        ApiResponse<AuthorDTO> response = new ApiResponse<>("Autor criado com sucesso!", createdAuthor);
        logger.info("Autor criado com sucesso.");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AuthorDTO>> getAuthorById(@PathVariable UUID id) {
        logger.info("Iniciando busca de autor por ID: {}", id);
        AuthorDTO authorDTO = authorService.findAuthorById(id);
        ApiResponse<AuthorDTO> response = new ApiResponse<>("Autor encontrado com sucesso!", authorDTO);
        logger.info("Autor encontrado com sucesso.");
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AuthorDTO>>> getAllAuthors() {
        logger.info("Iniciando busca por todos os autores.");
        List<AuthorDTO> authors = authorService.findAllAuthors();
        ApiResponse<List<AuthorDTO>> response = new ApiResponse<>("Lista de autores recuperada com sucesso!", authors);
        logger.info("Busca por todos os autores concluída.");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AuthorDTO>> updateAuthor(@PathVariable UUID id,
            @Valid @RequestBody AuthorDTO authorDTO) {
        logger.info("Iniciando atualização de autor para ID: {}", id);
        AuthorDTO updatedAuthorDTO = authorService.updateAuthor(id, authorDTO);
        ApiResponse<AuthorDTO> response = new ApiResponse<>("Autor atualizado com sucesso!", updatedAuthorDTO);
        logger.info("Autor atualizado com sucesso.");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable UUID id) {
        logger.info("Iniciando exclusão de autor por ID: {}", id);
        authorService.deleteAuthor(id);
        logger.info("Autor excluído com sucesso.");
        return ResponseEntity.noContent().build();
    }

}
