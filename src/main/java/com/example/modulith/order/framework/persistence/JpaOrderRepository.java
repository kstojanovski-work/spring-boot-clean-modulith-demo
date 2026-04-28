package com.example.modulith.order.framework.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaOrderRepository extends JpaRepository<OrderJpaEntity, UUID> {
}
