package com.example.modulith.order.interfaceadapter.web;

import com.example.modulith.order.usecase.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice(assignableTypes = OrderController.class)
class OrderApiErrorHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Map<String, String> notFound(OrderNotFoundException exception) {
        return Map.of("error", exception.getMessage());
    }
}
