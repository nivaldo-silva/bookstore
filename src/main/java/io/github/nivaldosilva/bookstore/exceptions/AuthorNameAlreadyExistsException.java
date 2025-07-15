package io.github.nivaldosilva.bookstore.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AuthorNameAlreadyExistsException extends RuntimeException {
    
    public AuthorNameAlreadyExistsException(String message) {
        super(message);
    }

}
