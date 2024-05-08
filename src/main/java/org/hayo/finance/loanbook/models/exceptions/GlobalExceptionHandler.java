package org.hayo.finance.loanbook.models.exceptions;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.hayo.finance.loanbook.dto.response.ApiErrorCauses;
import org.hayo.finance.loanbook.dto.response.ApiErrorSchema;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hayo.finance.loanbook.models.exceptions.ExceptionUtility.getApiErrorSchema;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorSchema> handleException(IllegalArgumentException e) {
        String message = e.getMessage();
        ApiErrorCauses causes = ApiErrorCauses.builder().name(e.getClass().getSimpleName())
                .detail(message)
                .location("BODY")
                .build();

        ApiErrorSchema schema = new ApiErrorSchema(HttpStatus.BAD_REQUEST.toString(),
                e.getMessage(),
                Collections.singletonList(causes));
        log.error("An error occurred {}", e.getMessage());

        log.error(e.getMessage());
        return new ResponseEntity<>(schema, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> notValid(ConstraintViolationException ex) {
        List<ApiErrorCauses> errors = new ArrayList<>();

        ex.getConstraintViolations().forEach(err -> errors.add(
                ApiErrorCauses.builder().detail(err.getMessage())
                        .location("body")
                        .name(err.getPropertyPath().toString())
                        .build()
        ));

        ApiErrorSchema schema = ApiErrorSchema.builder()
                .type(HttpStatus.BAD_REQUEST.toString())
                .detail("Validation error")
                .causes(errors).build();

        return new ResponseEntity<>(schema, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> notValid(MethodArgumentNotValidException ex) {
        List<ApiErrorCauses> errors = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(err -> errors.add(
                ApiErrorCauses.builder().detail(err.getDefaultMessage())
                        .location(err.getObjectName())
                        .detail(err.getDefaultMessage())
                        .value(String.valueOf(err.getRejectedValue()))
                        .name(err.getField())
                        .build()
        ));

        ApiErrorSchema schema = ApiErrorSchema.builder()
                .type(HttpStatus.BAD_REQUEST.toString())
                .detail("Validation error")
                .causes(errors).build();

        return new ResponseEntity<>(schema, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<?> notValid(HandlerMethodValidationException ex) {
        List<ApiErrorCauses> errors = new ArrayList<>();

        ex.getAllErrors().forEach(err -> errors.add(
                ApiErrorCauses.builder().detail(err.getDefaultMessage())
                        .location("body")
                        .name("").build()
        ));

        ApiErrorSchema schema = ApiErrorSchema.builder()
                .type(HttpStatus.BAD_REQUEST.toString())
                .detail("Validation error")
                .causes(errors).build();

        return new ResponseEntity<>(schema, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AbstractWebExceptions.class)
    public ResponseEntity<ApiErrorSchema> handleException(AbstractWebExceptions e) {
        ApiErrorSchema schema = getApiErrorSchema(e);

        log.error(e.getMessage());
        return new ResponseEntity<>(schema, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorSchema> handleException(Exception e) {
        String type = "INTERNAL_ERROR";
        String message = "An internal server error occurred, please try again later";
        ApiErrorCauses causes = ApiErrorCauses.builder().name(type)
                .detail(message)
                .location("BODY")
                .build();

        ApiErrorSchema schema = new ApiErrorSchema(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                "An error occurred",
                Collections.singletonList(causes));
        log.error("An error occurred {}", e.getMessage());
        return new ResponseEntity<>(schema, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
