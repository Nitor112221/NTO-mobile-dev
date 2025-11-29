package com.example.nto.controller;

import com.example.nto.exception.AuthException;
import com.example.nto.exception.PlaceAlreadyBookedException;
import com.example.nto.exception.PlaceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<Void> handleAuthException(AuthException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(PlaceAlreadyBookedException.class)
    public ResponseEntity<Void> handleConflict(PlaceAlreadyBookedException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(PlaceNotFoundException.class)
    public ResponseEntity<Void> handleBadRequest(PlaceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
