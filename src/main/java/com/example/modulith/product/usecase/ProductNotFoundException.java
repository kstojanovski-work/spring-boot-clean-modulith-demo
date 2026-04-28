package com.example.modulith.product.usecase;

import java.util.UUID;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(UUID id) {
        super("Product not found: " + id);
    }
}
