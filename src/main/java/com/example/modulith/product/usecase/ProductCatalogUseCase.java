package com.example.modulith.product.usecase;

import com.example.modulith.product.ProductCatalog;
import com.example.modulith.product.ProductView;
import com.example.modulith.product.usecase.gateway.ProductRepository;

import java.util.UUID;

public class ProductCatalogUseCase implements ProductCatalog {

    private final ProductRepository products;

    public ProductCatalogUseCase(ProductRepository products) {
        this.products = products;
    }

    @Override
    public ProductView getProduct(UUID id) {
        return products.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
}
