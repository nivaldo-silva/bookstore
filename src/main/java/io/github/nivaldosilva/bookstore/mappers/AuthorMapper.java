package io.github.nivaldosilva.bookstore.mappers;

import lombok.experimental.UtilityClass;
import io.github.nivaldosilva.bookstore.domain.entities.Author;
import io.github.nivaldosilva.bookstore.dtos.AuthorDTO;

@UtilityClass
public class AuthorMapper {

    public static Author toEntity(AuthorDTO authorDTO) {
        return Author.builder()
                .id(authorDTO.getId())
                .name(authorDTO.getName())
                .nationality(authorDTO.getNationality())
                .birthDate(authorDTO.getBirthDate())
                .biography(authorDTO.getBiography())
                .build();
    }

    public static AuthorDTO toDTO(Author author) {
        return AuthorDTO.builder()
                .id(author.getId())
                .name(author.getName())
                .nationality(author.getNationality())
                .birthDate(author.getBirthDate())
                .biography(author.getBiography())
                .build();
    }

}
