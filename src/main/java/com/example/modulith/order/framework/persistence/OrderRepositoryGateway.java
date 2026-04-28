package com.example.modulith.order.framework.persistence;

import com.example.modulith.order.usecase.gateway.OrderRepository;
import com.example.modulith.order.entity.Order;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class OrderRepositoryGateway implements OrderRepository {

    private final JpaOrderRepository orders;

    public OrderRepositoryGateway(JpaOrderRepository orders) {
        this.orders = orders;
    }

    @Override
    public Order save(Order order) {
        OrderJpaEntity entity = Objects.requireNonNull(OrderJpaEntity.from(order));
        return orders.save(entity).toDomain();
    }

    @Override
    public List<Order> findAll() {
        return orders.findAll().stream()
                .map(OrderJpaEntity::toDomain)
                .toList();
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return orders.findById(Objects.requireNonNull(id)).map(OrderJpaEntity::toDomain);
    }

    @Override
    public boolean existsById(UUID id) {
        return orders.existsById(Objects.requireNonNull(id));
    }

    @Override
    public void deleteById(UUID id) {
        orders.deleteById(Objects.requireNonNull(id));
    }
}
