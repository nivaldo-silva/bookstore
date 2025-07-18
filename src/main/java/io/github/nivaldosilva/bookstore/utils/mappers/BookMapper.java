package io.github.nivaldosilva.bookstore.utils.mappers;

import io.github.nivaldosilva.bookstore.domain.entities.Author;
import io.github.nivaldosilva.bookstore.domain.entities.Book;
import io.github.nivaldosilva.bookstore.dtos.request.CreateBookRequest;
import io.github.nivaldosilva.bookstore.dtos.response.BookResponse;
import io.github.nivaldosilva.bookstore.dtos.response.BookSummary;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BookMapper {

    public static Book toEntity(CreateBookRequest request, Author author) {
        return Book.builder()
                .id(request.getId())
                .isbn(request.getIsbn())
                .title(request.getTitle())
                .synopsis(request.getSynopsis())
                .genre(request.getGenre())
                .publicationDate(request.getPublicationDate())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .author(author)
                .build();
    }

    public static BookResponse toResponseDTO(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .synopsis(book.getSynopsis())
                .genre(book.getGenre())
                .publicationDate(book.getPublicationDate())
                .price(book.getPrice())
                .stockQuantity(book.getStockQuantity())
                .authorId(book.getAuthor() != null ? book.getAuthor().getId() : null)
                .authorName(book.getAuthor() != null ? book.getAuthor().getName() : null)
                .build();
    }

    public static BookSummary toSummaryDTO(Book book) {
        return BookSummary.builder()
                .id(book.getId())
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .authorName(book.getAuthor() != null ? book.getAuthor().getName() : null)
                .build();
    }
}
