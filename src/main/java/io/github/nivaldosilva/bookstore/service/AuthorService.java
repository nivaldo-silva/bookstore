package io.github.nivaldosilva.bookstore.service;

import io.github.nivaldosilva.bookstore.domain.entities.Author;
import io.github.nivaldosilva.bookstore.domain.repositories.AuthorRepository;
import io.github.nivaldosilva.bookstore.dtos.AuthorDTO;
import io.github.nivaldosilva.bookstore.exceptions.AuthorNameAlreadyExistsException;
import io.github.nivaldosilva.bookstore.exceptions.AuthorNotFoundException;
import io.github.nivaldosilva.bookstore.mappers.AuthorMapper;
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
    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        if (authorRepository.existsByName(authorDTO.getName())) {
            throw new AuthorNameAlreadyExistsException(
                    "Autor com o nome '" + authorDTO.getName() + "' já existe.");
        }

        Author author = AuthorMapper.toEntity(authorDTO);
        Author savedAuthor = authorRepository.save(author);

        return AuthorMapper.toDTO(savedAuthor); 
    }

    @Transactional(readOnly = true)
    public AuthorDTO findAuthorById(UUID id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Autor não encontrado com ID: " + id));

        return AuthorMapper.toDTO(author); 
    }

    @Transactional(readOnly = true)
    public List<AuthorDTO> findAllAuthors() {
        return authorRepository.findAll().stream()
                .map(AuthorMapper::toDTO) 
                .collect(Collectors.toList());
    }

    @Transactional
    public AuthorDTO updateAuthor(UUID id, AuthorDTO authorDTO) {
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Autor não encontrado com ID: " + id));

        if (!existingAuthor.getName().equals(authorDTO.getName())
                && authorRepository.existsByName(authorDTO.getName())) {
            throw new AuthorNameAlreadyExistsException(
                   "Autor com o nome '" + authorDTO.getName() + "' já existe.");
        }
        existingAuthor.setName(authorDTO.getName());
        existingAuthor.setNationality(authorDTO.getNationality());
        existingAuthor.setBirthDate(authorDTO.getBirthDate());
        existingAuthor.setBiography(authorDTO.getBiography());

        Author updatedAuthor = authorRepository.save(existingAuthor);
        return AuthorMapper.toDTO(updatedAuthor); 
    }


    @Transactional
    public void deleteAuthor(UUID id) {
        if (!authorRepository.existsById(id)) {
            throw new AuthorNotFoundException("Autor não encontrado com ID: " + id);
        }
        authorRepository.deleteById(id);
    }

}
