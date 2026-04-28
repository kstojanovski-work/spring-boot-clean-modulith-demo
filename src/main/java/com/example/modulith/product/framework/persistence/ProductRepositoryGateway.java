package com.example.modulith.product.framework.persistence;

import com.example.modulith.product.ProductView;
import com.example.modulith.product.usecase.gateway.ProductRepository;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class ProductRepositoryGateway implements ProductRepository {

    private final JpaProductRepository products;

    public ProductRepositoryGateway(JpaProductRepository products) {
        this.products = products;
    }

    @Override
    public Optional<ProductView> findById(UUID id) {
        return products.findById(Objects.requireNonNull(id)).map(ProductJpaEntity::toView);
    }
}
