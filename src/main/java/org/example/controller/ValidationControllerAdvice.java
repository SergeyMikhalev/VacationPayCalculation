package org.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import java.util.stream.Collectors;


@ControllerAdvice
public class ValidationControllerAdvice {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> handle(BindException e) {
        return ResponseEntity.badRequest().body(
                e.getAllErrors()
                        .stream()
                        .map(f -> f.getDefaultMessage())
                        .collect(Collectors.toList())
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handle(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
