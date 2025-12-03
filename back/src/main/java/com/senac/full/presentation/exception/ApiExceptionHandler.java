package com.senac.full.presentation.exception;

import com.senac.full.application.exception.BusinessException;
import com.senac.full.application.exception.NotFoundException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String,String>> handleNotFound(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", ex.getMessage()));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String,String>> handleBusiness(BusinessException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", ex.getMessage()));
    }
}