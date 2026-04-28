package com.example.modulith.shared.interfaceadapter.web;

import com.example.modulith.shared.DomainException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;

import java.util.Map;

@RestControllerAdvice
class ApiErrorHandler {

    @ExceptionHandler({DomainException.class, IllegalArgumentException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Map<String, String> badRequest(Exception exception) {
        return Map.of("error", exception.getMessage());
    }
}
