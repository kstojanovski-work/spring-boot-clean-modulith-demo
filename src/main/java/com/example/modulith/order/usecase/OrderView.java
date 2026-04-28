package com.example.modulith.order.usecase;

import com.example.modulith.order.entity.Order;
import com.example.modulith.order.entity.OrderStatus;
import com.example.modulith.shared.Money;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record OrderView(
        UUID id,
        String orderNumber,
        String customerEmail,
        List<Line> lines,
        OrderStatus status,
        Instant placedAt,
        Money total
) {

    static OrderView from(Order order) {
        return new OrderView(
                order.id(),
                order.orderNumber(),
                order.customerEmail(),
                order.lines().stream()
                        .map(line -> new Line(line.productId(), line.productName(), line.quantity(), line.unitPrice(), line.subtotal()))
                        .toList(),
                order.status(),
                order.placedAt(),
                order.total()
        );
    }

    public record Line(UUID productId, String productName, int quantity, Money unitPrice, Money subtotal) {
    }
}
