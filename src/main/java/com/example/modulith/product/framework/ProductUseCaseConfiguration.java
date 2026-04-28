package com.example.modulith.product.framework;

import com.example.modulith.product.ProductCatalog;
import com.example.modulith.product.framework.transaction.TransactionalProductCatalog;
import com.example.modulith.product.usecase.ProductCatalogUseCase;
import com.example.modulith.product.usecase.gateway.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ProductUseCaseConfiguration {

    @Bean
    ProductCatalog productCatalog(ProductRepository products) {
        return new TransactionalProductCatalog(new ProductCatalogUseCase(products));
    }
}
