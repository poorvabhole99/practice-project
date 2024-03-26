package com.practice.practiceProject.exception;

import com.practice.practiceProject.response.ErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PracticeProjectException.class)
    public ResponseEntity<ErrorResponse> handlePracticeProjectException(
            final PracticeProjectException exception) {
        log.error(" Exception {0}: ", exception);
        return new ResponseEntity<>(exception.getErrorResponse(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(
            final UserNotFoundException exception) {
        log.error(" User not found exception {0}: ", exception);
        return new ResponseEntity<>(exception.getErrorResponse(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String errorMessage = String.format("%s: %s", violation.getPropertyPath(), violation.getMessage());
            errors.add(errorMessage);
        }
        ErrorResponse errorResponse = new ErrorResponse("Validation failed", false,errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
