package com.example.modulith.order.usecase.gateway;

import com.example.modulith.order.entity.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {

    Order save(Order order);

    List<Order> findAll();

    Optional<Order> findById(UUID id);

    boolean existsById(UUID id);

    void deleteById(UUID id);
}
