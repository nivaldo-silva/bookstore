package io.github.nivaldosilva.bookstore.mappers;

import io.github.nivaldosilva.bookstore.domain.entities.Author;
import io.github.nivaldosilva.bookstore.domain.entities.Book;
import io.github.nivaldosilva.bookstore.dtos.BookDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BookMapper {

    public static Book toEntity(BookDTO bookDTO, Author author) {
        return Book.builder()
                .id(bookDTO.getId())
                .isbn(bookDTO.getIsbn())
                .title(bookDTO.getTitle())
                .synopsis(bookDTO.getSynopsis())
                .genre(bookDTO.getGenre())
                .publicationDate(bookDTO.getPublicationDate())
                .price(bookDTO.getPrice())
                .stockQuantity(bookDTO.getStockQuantity())
                .author(author)
                .build();
    }

    public static BookDTO toDTO(Book book) {
        return BookDTO.builder()
                .id(book.getId())
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .synopsis(book.getSynopsis())
                .genre(book.getGenre())
                .publicationDate(book.getPublicationDate())
                .price(book.getPrice())
                .stockQuantity(book.getStockQuantity())
                .authorId(book.getAuthor() != null ? book.getAuthor().getId() : null)
                .build();
    }

}
