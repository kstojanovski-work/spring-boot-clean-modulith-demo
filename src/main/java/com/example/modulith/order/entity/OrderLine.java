package com.example.modulith.order.entity;

import com.example.modulith.shared.Money;

import java.util.Objects;
import java.util.UUID;

public record OrderLine(UUID productId, String productName, int quantity, Money unitPrice) {

    public OrderLine {
        Objects.requireNonNull(productId, "productId must not be null");
        Objects.requireNonNull(productName, "productName must not be null");
        Objects.requireNonNull(unitPrice, "unitPrice must not be null");
        if (productName.isBlank()) {
            throw new IllegalArgumentException("productName must not be blank");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity must be positive");
        }
    }

    public Money subtotal() {
        return unitPrice.multiply(quantity);
    }
}
