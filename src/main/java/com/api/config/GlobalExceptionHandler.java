package com.api.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.api.exception.ResourceNotFoundException;

import org.springframework.web.bind.MethodArgumentNotValidException;

import jakarta.validation.ConstraintViolationException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
        Map<String, Object> response = createErrorResponse("Internal Server Error", ex.getMessage(), request);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        Map<String, Object> response = createErrorResponse("Resource Not Found", ex.getMessage(), request);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> dataIntegrityViolationExceptionHandler(DataIntegrityViolationException ex,
            WebRequest request) {
        String message = "Data Integrity Violation";
        if (ex.getMostSpecificCause().getMessage().contains("unique constraint") ||
                ex.getMostSpecificCause().getMessage().contains("duplicate key")) {
            message = "A product with this name already exists. Please choose a unique name.";
        }
        Map<String, Object> response = createErrorResponse(message, ex.getMostSpecificCause().getMessage(), request);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
            WebRequest request) {
        @SuppressWarnings("null")
        String error = "The parameter '" + ex.getName() + "' should be of type " + ex.getRequiredType().getSimpleName();
        Map<String, Object> response = createErrorResponse("Method Argument Type Mismatch", error, request);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        error -> error.getField(),
                        error -> error.getDefaultMessage()));
        Map<String, Object> response = createErrorResponse("Validation Error", errors, request);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        Map<String, String> errors = ex.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        violation -> violation.getMessage()));
        Map<String, Object> response = createErrorResponse("Validation Error", errors, request);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Map<String, Object> createErrorResponse(String message, Object details, WebRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", new Date());
        response.put("status", "error");
        response.put("message", message);
        response.put("details", details);
        response.put("path", request.getDescription(false));
        return response;
    }
}