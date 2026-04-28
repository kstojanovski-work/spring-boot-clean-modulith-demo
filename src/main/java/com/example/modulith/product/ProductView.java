package com.example.modulith.product;

import com.example.modulith.shared.Money;

import java.util.UUID;

public record ProductView(UUID id, String name, Money price) {
}
