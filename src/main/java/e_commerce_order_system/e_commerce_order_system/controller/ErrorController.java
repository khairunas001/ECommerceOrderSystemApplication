package e_commerce_order_system.e_commerce_order_system.controller;

import e_commerce_order_system.e_commerce_order_system.exception.EntityNotFoundException;
import e_commerce_order_system.e_commerce_order_system.model.web_response.WebResponseErrors;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorController {

    // @Valid @RequestBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<WebResponseErrors<?>> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex) {

        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + " " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity.badRequest().body(
                WebResponseErrors.builder()
                        .status(HttpStatus.BAD_REQUEST.toString())
                        .errors(errors)
                        .build()
        );
    }

    // @Validated di param / path / request param
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<WebResponseErrors<?>> handleConstraintViolation(
            ConstraintViolationException ex) {

        String errors = ex.getConstraintViolations()
                .stream()
                .map(v -> v.getPropertyPath() + " " + v.getMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity.badRequest().body(
                WebResponseErrors.builder()
                        .status(HttpStatus.BAD_REQUEST.toString())
                        .errors(errors)
                        .build()
        );
    }

    // Custom not found / business error
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<WebResponseErrors<?>> handleNotFound(
            EntityNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                WebResponseErrors.builder()
                        .status(HttpStatus.NOT_FOUND.toString())
                        .errors(ex.getMessage())
                        .build()
        );
    }

    // ResponseStatusException (throw manual)
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<WebResponseErrors<?>> handleResponseStatus(
            ResponseStatusException ex) {

        return ResponseEntity.status(ex.getStatusCode()).body(
                WebResponseErrors.builder()
                        .status(ex.getStatusCode().toString())
                        .errors(ex.getReason())
                        .build()
        );
    }

    // fallback TERAKHIR
    @ExceptionHandler(Exception.class)
    public ResponseEntity<WebResponseErrors<?>> handleGeneralException(Exception ex) {

        return ResponseEntity.internalServerError().body(
                WebResponseErrors.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                        .errors("Internal server error")
                        .build()
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<WebResponseErrors<?>> handleIllegalArgumentException(
            IllegalArgumentException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(WebResponseErrors.builder()
                        .status(HttpStatus.BAD_REQUEST.toString())
                        .errors(exception.getMessage())
                        .build());
    }

}

