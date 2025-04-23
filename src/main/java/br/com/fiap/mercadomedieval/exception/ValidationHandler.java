package br.com.fiap.mercadomedieval.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handler global para validações com @Valid.
 * Captura e retorna erros de validação em formato estruturado e amigável.
 */
@RestControllerAdvice
public class ValidationHandler {

    /**
     * DTO interno para representar cada erro de campo.
     * Ex: { "field": "nome", "message": "O nome é obrigatório" }
     */
    record ValidationError(String field, String message) {
        public ValidationError(FieldError fieldError) {
            this(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }

    /**
     * Trata exceções de validação de DTOs anotados com @Valid.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ValidationError> handle(MethodArgumentNotValidException e) {
        return e.getFieldErrors()
                .stream()
                .map(ValidationError::new)
                .toList();
    }
}
