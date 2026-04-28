package com.example.modulith.order.usecase;

import java.util.List;
import java.util.UUID;

public interface OrderInputBoundary {

    OrderView placeOrder(PlaceOrderRequest request);

    List<OrderView> getOrders();

    OrderView getOrder(UUID id);

    OrderView cancelOrder(UUID id);

    void deleteOrder(UUID id);
}
