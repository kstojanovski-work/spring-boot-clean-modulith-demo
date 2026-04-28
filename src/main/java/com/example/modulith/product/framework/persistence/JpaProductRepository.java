package com.example.modulith.product.framework.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaProductRepository extends JpaRepository<ProductJpaEntity, UUID> {
}
