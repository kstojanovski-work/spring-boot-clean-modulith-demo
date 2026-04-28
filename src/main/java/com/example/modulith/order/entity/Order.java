package com.example.modulith.order.entity;

import com.example.modulith.shared.DomainException;
import com.example.modulith.shared.Money;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Order {

    private final UUID id;
    private final String orderNumber;
    private final String customerEmail;
    private final List<OrderLine> lines;
    private final OrderStatus status;
    private final Instant placedAt;

    private Order(UUID id, String orderNumber, String customerEmail, List<OrderLine> lines, OrderStatus status, Instant placedAt) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.orderNumber = requireText(orderNumber, "orderNumber");
        this.customerEmail = requireText(customerEmail, "customerEmail");
        this.lines = List.copyOf(lines);
        this.status = Objects.requireNonNull(status, "status must not be null");
        this.placedAt = Objects.requireNonNull(placedAt, "placedAt must not be null");
        if (this.lines.isEmpty()) {
            throw new DomainException("An order must contain at least one line");
        }
    }

    public static Order place(String orderNumber, String customerEmail, List<OrderLine> lines, Instant placedAt) {
        return new Order(UUID.randomUUID(), orderNumber, customerEmail, lines, OrderStatus.PLACED, placedAt);
    }

    public static Order restore(UUID id, String orderNumber, String customerEmail, List<OrderLine> lines, OrderStatus status, Instant placedAt) {
        return new Order(id, orderNumber, customerEmail, lines, status, placedAt);
    }

    public Order cancel() {
        if (status == OrderStatus.CANCELLED) {
            throw new DomainException("Order is already cancelled");
        }
        return new Order(id, orderNumber, customerEmail, lines, OrderStatus.CANCELLED, placedAt);
    }

    public Money total() {
        return lines.stream().map(OrderLine::subtotal).reduce(Money::add).orElseThrow();
    }

    public UUID id() {
        return id;
    }

    public String orderNumber() {
        return orderNumber;
    }

    public String customerEmail() {
        return customerEmail;
    }

    public List<OrderLine> lines() {
        return lines;
    }

    public OrderStatus status() {
        return status;
    }

    public Instant placedAt() {
        return placedAt;
    }

    private static String requireText(String value, String fieldName) {
        Objects.requireNonNull(value, fieldName + " must not be null");
        if (value.isBlank()) {
            throw new DomainException(fieldName + " must not be blank");
        }
        return value;
    }
}
