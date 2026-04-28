package com.example.modulith.product.interfaceadapter.web;

import com.example.modulith.product.ProductCatalog;
import com.example.modulith.product.ProductView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/products")
class ProductController {

    private final ProductCatalog products;

    ProductController(ProductCatalog products) {
        this.products = products;
    }

    @GetMapping("/{id}")
    ProductView getProduct(@PathVariable UUID id) {
        return products.getProduct(id);
    }
}
