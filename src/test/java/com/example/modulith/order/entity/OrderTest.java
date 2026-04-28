package com.example.modulith.order.entity;

import com.example.modulith.shared.DomainException;
import com.example.modulith.shared.Money;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderTest {

    @Test
    void calculatesTotalFromOrderLines() {
        Order order = Order.place(
                "ORDER-1",
                "learner@example.com",
                List.of(
                        new OrderLine(UUID.randomUUID(), "Book", 2, Money.eur("42.00")),
                        new OrderLine(UUID.randomUUID(), "Mug", 1, Money.eur("12.90"))
                ),
                Instant.parse("2026-04-28T08:00:00Z")
        );

        assertThat(order.total()).isEqualTo(Money.eur("96.90"));
    }

    @Test
    void rejectsOrdersWithoutLines() {
        assertThatThrownBy(() -> Order.place(
                "ORDER-1",
                "learner@example.com",
                List.of(),
                Instant.parse("2026-04-28T08:00:00Z")
        ))
                .isInstanceOf(DomainException.class)
                .hasMessage("An order must contain at least one line");
    }

    @Test
    void rejectsCancellingAlreadyCancelledOrder() {
        Order cancelled = Order.place(
                "ORDER-1",
                "learner@example.com",
                List.of(new OrderLine(UUID.randomUUID(), "Book", 1, Money.eur("42.00"))),
                Instant.parse("2026-04-28T08:00:00Z")
        ).cancel();

        assertThatThrownBy(cancelled::cancel)
                .isInstanceOf(DomainException.class)
                .hasMessage("Order is already cancelled");
    }
}
