package io.github.nivaldosilva.bookstore.domain.enums;

import lombok.Getter;

@Getter
public enum Genre {

    ADVENTURE("Adventure"),
    DRAMA("Drama"),
    COMEDY("Comedy"),
    FICTION("Fiction"),
    NON_FICTION("Non-Fiction"),
    MYSTERY("Mystery"),
    FANTASY("Fantasy"),
    SCIENCE_FICTION("Science Fiction"),
    BIOGRAPHY("Biography"),
    HISTORY("History"),
    ROMANCE("Romance"),
    HORROR("Horror"),
    SELF_HELP("Self-Help");

    private final String genre;

    Genre(String genre) {
        this.genre = genre;
    }

}
