package com.example.modulith.order.usecase;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public record PlaceOrderRequest(String customerEmail, List<Item> items) {

    public PlaceOrderRequest {
        Objects.requireNonNull(customerEmail, "customerEmail must not be null");
        if (customerEmail.isBlank()) {
            throw new IllegalArgumentException("customerEmail must not be blank");
        }
        items = List.copyOf(items);
        if (items.isEmpty()) {
            throw new IllegalArgumentException("items must not be empty");
        }
    }

    public record Item(UUID productId, int quantity) {

        public Item {
            Objects.requireNonNull(productId, "productId must not be null");
            if (quantity <= 0) {
                throw new IllegalArgumentException("quantity must be positive");
            }
        }
    }
}
