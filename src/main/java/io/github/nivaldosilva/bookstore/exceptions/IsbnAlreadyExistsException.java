package io.github.nivaldosilva.bookstore.exceptions;

public class IsbnAlreadyExistsException extends RuntimeException {
    
    public IsbnAlreadyExistsException(String message) {
        super(message);
    }

}
