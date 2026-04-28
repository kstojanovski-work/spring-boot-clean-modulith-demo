package com.example.modulith.order.framework.number;

import com.example.modulith.order.usecase.gateway.OrderNumberGenerator;

import java.util.UUID;

public class UuidOrderNumberGenerator implements OrderNumberGenerator {

    @Override
    public String nextOrderNumber() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
