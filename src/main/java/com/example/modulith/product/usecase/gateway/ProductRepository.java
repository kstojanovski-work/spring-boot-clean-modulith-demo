package com.example.modulith.product.usecase.gateway;

import com.example.modulith.product.ProductView;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {

    Optional<ProductView> findById(UUID id);
}
