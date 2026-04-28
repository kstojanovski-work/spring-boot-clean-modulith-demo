package com.example.modulith.order.usecase;

import com.example.modulith.order.entity.Order;
import com.example.modulith.order.usecase.gateway.OrderNumberGenerator;
import com.example.modulith.order.usecase.gateway.OrderRepository;
import com.example.modulith.product.ProductCatalog;
import com.example.modulith.product.ProductView;
import com.example.modulith.shared.Money;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderUseCaseTest {

    private static final UUID PRODUCT_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final Instant NOW = Instant.parse("2026-04-28T08:00:00Z");

    private final InMemoryOrderRepository orders = new InMemoryOrderRepository();
    private final ProductCatalog products = id -> new ProductView(id, "Clean Architecture Book", Money.eur("42.00"));
    private final OrderNumberGenerator orderNumbers = () -> "ORDER-1";
    private final OrderUseCase useCase = new OrderUseCase(
            orders,
            products,
            orderNumbers,
            Clock.fixed(NOW, ZoneOffset.UTC)
    );

    @Test
    void placesOrderWithProductSnapshotAndGeneratedOrderNumber() {
        OrderView order = useCase.placeOrder(new PlaceOrderRequest(
                "learner@example.com",
                List.of(new PlaceOrderRequest.Item(PRODUCT_ID, 2))
        ));

        assertThat(order.orderNumber()).isEqualTo("ORDER-1");
        assertThat(order.customerEmail()).isEqualTo("learner@example.com");
        assertThat(order.placedAt()).isEqualTo(NOW);
        assertThat(order.total()).isEqualTo(Money.eur("84.00"));
        assertThat(order.lines()).singleElement().satisfies(line -> {
            assertThat(line.productId()).isEqualTo(PRODUCT_ID);
            assertThat(line.productName()).isEqualTo("Clean Architecture Book");
            assertThat(line.quantity()).isEqualTo(2);
            assertThat(line.unitPrice()).isEqualTo(Money.eur("42.00"));
        });
        assertThat(orders.saved).hasSize(1);
    }

    @Test
    void throwsWhenOrderDoesNotExist() {
        UUID missingOrderId = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

        assertThatThrownBy(() -> useCase.getOrder(missingOrderId))
                .isInstanceOf(OrderNotFoundException.class)
                .hasMessage("Order not found: " + missingOrderId);
    }

    @Test
    void listsPlacedOrders() {
        OrderView firstOrder = useCase.placeOrder(new PlaceOrderRequest(
                "learner@example.com",
                List.of(new PlaceOrderRequest.Item(PRODUCT_ID, 1))
        ));
        OrderView secondOrder = useCase.placeOrder(new PlaceOrderRequest(
                "admin@example.com",
                List.of(new PlaceOrderRequest.Item(PRODUCT_ID, 2))
        ));

        assertThat(useCase.getOrders())
                .extracting(OrderView::id)
                .containsExactly(firstOrder.id(), secondOrder.id());
    }

    private static class InMemoryOrderRepository implements OrderRepository {

        private final List<Order> saved = new ArrayList<>();

        @Override
        public Order save(Order order) {
            saved.removeIf(savedOrder -> savedOrder.id().equals(order.id()));
            saved.add(order);
            return order;
        }

        @Override
        public List<Order> findAll() {
            return List.copyOf(saved);
        }

        @Override
        public Optional<Order> findById(UUID id) {
            return saved.stream()
                    .filter(order -> order.id().equals(id))
                    .findFirst();
        }

        @Override
        public boolean existsById(UUID id) {
            return findById(id).isPresent();
        }

        @Override
        public void deleteById(UUID id) {
            saved.removeIf(order -> order.id().equals(id));
        }
    }
}
