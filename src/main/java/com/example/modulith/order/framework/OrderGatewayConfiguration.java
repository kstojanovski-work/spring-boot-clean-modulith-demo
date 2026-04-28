package com.example.modulith.order.framework;

import com.example.modulith.order.framework.number.UuidOrderNumberGenerator;
import com.example.modulith.order.framework.persistence.JpaOrderRepository;
import com.example.modulith.order.framework.persistence.OrderRepositoryGateway;
import com.example.modulith.order.usecase.gateway.OrderNumberGenerator;
import com.example.modulith.order.usecase.gateway.OrderRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class OrderGatewayConfiguration {

    @Bean
    OrderRepository orderRepository(JpaOrderRepository orders) {
        return new OrderRepositoryGateway(orders);
    }

    @Bean
    OrderNumberGenerator orderNumberGenerator() {
        return new UuidOrderNumberGenerator();
    }
}
