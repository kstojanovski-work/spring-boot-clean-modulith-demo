package com.example.modulith.order.framework.transaction;

import com.example.modulith.order.usecase.OrderInputBoundary;
import com.example.modulith.order.usecase.OrderView;
import com.example.modulith.order.usecase.PlaceOrderRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public class TransactionalOrderUseCase implements OrderInputBoundary {

    private final OrderInputBoundary delegate;

    public TransactionalOrderUseCase(OrderInputBoundary delegate) {
        this.delegate = delegate;
    }

    @Override
    @Transactional
    public OrderView placeOrder(PlaceOrderRequest request) {
        return delegate.placeOrder(request);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderView> getOrders() {
        return delegate.getOrders();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderView getOrder(UUID id) {
        return delegate.getOrder(id);
    }

    @Override
    @Transactional
    public OrderView cancelOrder(UUID id) {
        return delegate.cancelOrder(id);
    }

    @Override
    @Transactional
    public void deleteOrder(UUID id) {
        delegate.deleteOrder(id);
    }
}
