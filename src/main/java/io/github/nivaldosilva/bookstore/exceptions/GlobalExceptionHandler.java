package io.github.nivaldosilva.bookstore.exceptions;

import io.github.nivaldosilva.bookstore.dtos.Common.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex,
            WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message("Um ou mais campos possuem erros de validação. Verifique os detalhes.")
                .path(path)
                .details(errors)
                .build();

        logger.error("Validation error: {}", errorResponse.getMessage(), ex);
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler({
            IsbnAlreadyExistsException.class,
            AuthorNameAlreadyExistsException.class,
            EmailAlreadyExistsException.class
    })
    public ResponseEntity<ErrorResponse> handleConflictExceptions(RuntimeException ex, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(ex.getMessage())
                .path(path)
                .build();

        logger.error("Conflict error: {}", errorResponse.getMessage(), ex);
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler({
            AuthorNotFoundException.class,
            BookNotFoundException.class,
            CustomerNotFoundException.class,
            OrderNotFoundException.class,
            OrderItemNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFoundExceptions(RuntimeException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(ex.getMessage())
                .path(path)
                .build();

        logger.error("Not Found error: {}", errorResponse.getMessage(), ex);
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientStockException(InsufficientStockException ex,
            WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(ex.getMessage())
                .path(path)
                .build();

        logger.error("Insufficient Stock error: {}", errorResponse.getMessage(), ex);
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex,
            WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(ex.getMessage())
                .path(path)
                .build();

        logger.error("Illegal Argument error: {}", errorResponse.getMessage(), ex);
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message("Ocorreu um erro interno no servidor. Por favor, tente novamente mais tarde.")
                .path(path)
                .build();

        logger.error("Generic internal server error: {}", errorResponse.getMessage(), ex);
        return new ResponseEntity<>(errorResponse, status);
    }
}
