package io.github.nivaldosilva.bookstore.service;

import io.github.nivaldosilva.bookstore.domain.entities.Author;
import io.github.nivaldosilva.bookstore.domain.repositories.AuthorRepository;
import io.github.nivaldosilva.bookstore.dtos.request.CreateAuthorRequest;
import io.github.nivaldosilva.bookstore.dtos.response.AuthorDetailsResponse;
import io.github.nivaldosilva.bookstore.dtos.response.AuthorSummary;
import io.github.nivaldosilva.bookstore.exceptions.AuthorNameAlreadyExistsException;
import io.github.nivaldosilva.bookstore.exceptions.AuthorNotFoundException;
import io.github.nivaldosilva.bookstore.utils.mappers.AuthorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Transactional
    public AuthorDetailsResponse createAuthor(CreateAuthorRequest request) {
        if (authorRepository.existsByName(request.getName())) {
            throw new AuthorNameAlreadyExistsException(
                    "Autor com o nome '" + request.getName() + "' já existe.");
        }

        Author author = AuthorMapper.toEntity(request);
        Author savedAuthor = authorRepository.save(author);

        return AuthorMapper.toDetailsResponseDTO(savedAuthor);
    }

    @Transactional(readOnly = true)
    public AuthorDetailsResponse findAuthorById(UUID id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Autor não encontrado com ID: " + id));

        return AuthorMapper.toDetailsResponseDTO(author);
    }

    @Transactional(readOnly = true)
    public List<AuthorSummary> findAllAuthors() {
        return authorRepository.findAll().stream()
                .map(AuthorMapper::toSummaryDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public AuthorDetailsResponse updateAuthor(UUID id, CreateAuthorRequest request) {
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Autor não encontrado com ID: " + id));

        if (!existingAuthor.getName().equals(request.getName())
                && authorRepository.existsByName(request.getName())) {
            throw new AuthorNameAlreadyExistsException(
                    "Autor com o nome '" + request.getName() + "' já existe.");
        }
        existingAuthor.setName(request.getName());
        existingAuthor.setNationality(request.getNationality());
        existingAuthor.setBirthDate(request.getBirthDate());
        existingAuthor.setBiography(request.getBiography());

        Author updatedAuthor = authorRepository.save(existingAuthor);
        return AuthorMapper.toDetailsResponseDTO(updatedAuthor);
    }

    @Transactional
    public void deleteAuthor(UUID id) {
        if (!authorRepository.existsById(id)) {
            throw new AuthorNotFoundException("Autor não encontrado com ID: " + id);
        }
        authorRepository.deleteById(id);
    }
}
