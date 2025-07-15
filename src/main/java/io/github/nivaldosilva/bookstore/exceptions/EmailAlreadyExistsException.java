package io.github.nivaldosilva.bookstore.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
    
    public EmailAlreadyExistsException(String message) {
        super(message);
    }

}
