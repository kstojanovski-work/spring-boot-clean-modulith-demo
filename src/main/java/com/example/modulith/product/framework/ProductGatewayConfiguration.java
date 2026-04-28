package com.example.modulith.product.framework;

import com.example.modulith.product.framework.persistence.JpaProductRepository;
import com.example.modulith.product.framework.persistence.ProductRepositoryGateway;
import com.example.modulith.product.usecase.gateway.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ProductGatewayConfiguration {

    @Bean
    ProductRepository productRepository(JpaProductRepository products) {
        return new ProductRepositoryGateway(products);
    }
}
