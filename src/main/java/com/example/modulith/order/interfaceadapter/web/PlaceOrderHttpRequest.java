package com.example.modulith.order.interfaceadapter.web;

import com.example.modulith.order.usecase.PlaceOrderRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

record PlaceOrderHttpRequest(
        @Email @NotNull String customerEmail,
        @NotEmpty List<@Valid Item> items
) {

    PlaceOrderRequest toUseCaseRequest() {
        return new PlaceOrderRequest(
                customerEmail,
                items.stream()
                        .map(item -> new PlaceOrderRequest.Item(item.productId(), item.quantity()))
                        .toList()
        );
    }

    record Item(@NotNull UUID productId, @Min(1) int quantity) {
    }
}
