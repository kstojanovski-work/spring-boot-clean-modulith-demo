package com.example.modulith.order.framework;

import com.example.modulith.order.framework.transaction.TransactionalOrderUseCase;
import com.example.modulith.order.usecase.OrderInputBoundary;
import com.example.modulith.order.usecase.OrderUseCase;
import com.example.modulith.order.usecase.gateway.OrderNumberGenerator;
import com.example.modulith.order.usecase.gateway.OrderRepository;
import com.example.modulith.product.ProductCatalog;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
class OrderUseCaseConfiguration {

    @Bean
    OrderInputBoundary orderInputBoundary(
            OrderRepository orders,
            ProductCatalog products,
            OrderNumberGenerator orderNumbers,
            Clock clock
    ) {
        OrderUseCase useCase = new OrderUseCase(orders, products, orderNumbers, clock);
        return new TransactionalOrderUseCase(useCase);
    }
}
