package com.bbl.test.application.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleNotFound(UserNotFoundException ex) {
    Map<String, Object> body = new HashMap<>();
    body.put("error", "Not Found");
    body.put("message", ex.getMessage());
    body.put("userId", ex.id());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
    List<Map<String, String>> errors = ex.getBindingResult().getFieldErrors().stream()
        .map(f -> Map.of("field", f.getField(), "message", f.getDefaultMessage()))
        .toList();
    Map<String, Object> body = new HashMap<>();
    body.put("error", "Bad Request");
    body.put("message", "Validation failed");
    body.put("errors", errors);
    return ResponseEntity.badRequest().body(body);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<Map<String, Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
    Map<String, Object> body = new HashMap<>();
    body.put("error", "Bad Request");
    body.put("message", "Invalid parameter: " + ex.getName());
    return ResponseEntity.badRequest().body(body);
  }
}