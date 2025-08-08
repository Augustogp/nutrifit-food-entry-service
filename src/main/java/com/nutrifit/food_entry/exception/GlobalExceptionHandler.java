package com.nutrifit.food_entry.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                "INVALID_ARGUMENT",
                e.getMessage(),
                "An invalid argument was provided."
        );

        log.error("Illegal argument exception: {}", e.getMessage(), e);

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException e) {

        List<ErrorResponse.FieldErrorDetail> fieldErrors = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ErrorResponse.FieldErrorDetail(
                        fieldError.getField(),
                        fieldError.getDefaultMessage()
                ))
                .toList();

        ErrorResponse errorResponse = new ErrorResponse(
                "Validation failed",
                "VALIDATION_ERROR",
                "One or more validation errors occurred.",
                fieldErrors
        );

        log.error("Validation exception: {}", e.getMessage(), e);

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Resource not found",
                "RESOURCE_NOT_FOUND",
                e.getMessage()
        );

        log.error("Resource not found exception: {}", e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateResourceException(DuplicateResourceException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Resource already exists",
                "DUPLICATE_RESOURCE",
                e.getMessage()
        );

        log.error("Duplicate resource exception: {}", e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFormatException(InvalidFormatException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Invalid format",
                "INVALID_FORMAT",
                e.getMessage()
        );

        log.error("Invalid format exception: {}", e.getMessage(), e);

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(ExternalServiceException.class)
    public ResponseEntity<ErrorResponse> handleExternalServiceException(ExternalServiceException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                "External service error",
                "EXTERNAL_SERVICE_ERROR",
                e.getMessage()
        );

        log.error("External service exception: {}", e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorResponse);
    }

}
