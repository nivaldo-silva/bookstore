package io.github.nivaldosilva.bookstore.utils.mappers;

import lombok.experimental.UtilityClass;
import io.github.nivaldosilva.bookstore.domain.entities.Author;
import io.github.nivaldosilva.bookstore.dtos.request.CreateAuthorRequest;
import io.github.nivaldosilva.bookstore.dtos.response.AuthorDetailsResponse;
import io.github.nivaldosilva.bookstore.dtos.response.AuthorSummary;

@UtilityClass
public class AuthorMapper {

    public static Author toEntity(CreateAuthorRequest request) {
        return Author.builder()
                .id(request.getId())
                .name(request.getName())
                .nationality(request.getNationality())
                .birthDate(request.getBirthDate())
                .biography(request.getBiography())
                .build();
    }

    public static AuthorDetailsResponse toDetailsResponseDTO(Author author) {
        return AuthorDetailsResponse.builder()
                .id(author.getId())
                .name(author.getName())
                .nationality(author.getNationality())
                .birthDate(author.getBirthDate())
                .biography(author.getBiography())
                .build();
    }

    public static AuthorSummary toSummaryDTO(Author author) {
        return AuthorSummary.builder()
                .id(author.getId())
                .name(author.getName())
                .nationality(author.getNationality())
                .build();
    }
}
