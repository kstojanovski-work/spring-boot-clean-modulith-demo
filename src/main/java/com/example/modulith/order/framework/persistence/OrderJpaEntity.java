package com.example.modulith.order.framework.persistence;

import com.example.modulith.order.entity.Order;
import com.example.modulith.order.entity.OrderStatus;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
class OrderJpaEntity {

    @Id
    private UUID id;

    private String orderNumber;

    private String customerEmail;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "order_lines", joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderLineJpaEmbeddable> lines = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Instant placedAt;

    protected OrderJpaEntity() {
    }

    private OrderJpaEntity(UUID id, String orderNumber, String customerEmail, List<OrderLineJpaEmbeddable> lines, OrderStatus status, Instant placedAt) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.customerEmail = customerEmail;
        this.lines = new ArrayList<>(lines);
        this.status = status;
        this.placedAt = placedAt;
    }

    static OrderJpaEntity from(Order order) {
        return new OrderJpaEntity(
                order.id(),
                order.orderNumber(),
                order.customerEmail(),
                order.lines().stream().map(OrderLineJpaEmbeddable::from).toList(),
                order.status(),
                order.placedAt()
        );
    }

    Order toDomain() {
        return Order.restore(id, orderNumber, customerEmail, lines.stream().map(OrderLineJpaEmbeddable::toDomain).toList(), status, placedAt);
    }
}
