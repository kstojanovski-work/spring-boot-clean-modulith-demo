package com.example.modulith.order.usecase;

import com.example.modulith.order.usecase.gateway.OrderNumberGenerator;
import com.example.modulith.order.usecase.gateway.OrderRepository;
import com.example.modulith.order.entity.Order;
import com.example.modulith.order.entity.OrderLine;
import com.example.modulith.product.ProductCatalog;
import com.example.modulith.product.ProductView;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class OrderUseCase implements OrderInputBoundary {

    private final OrderRepository orders;
    private final ProductCatalog products;
    private final OrderNumberGenerator orderNumbers;
    private final Clock clock;

    public OrderUseCase(OrderRepository orders, ProductCatalog products, OrderNumberGenerator orderNumbers, Clock clock) {
        this.orders = orders;
        this.products = products;
        this.orderNumbers = orderNumbers;
        this.clock = clock;
    }

    @Override
    public OrderView placeOrder(PlaceOrderRequest request) {
        List<OrderLine> lines = request.items().stream()
                .map(item -> toOrderLine(products.getProduct(item.productId()), item.quantity()))
                .toList();
        Order order = Order.place(orderNumbers.nextOrderNumber(), request.customerEmail(), lines, Instant.now(clock));
        return OrderView.from(orders.save(order));
    }

    @Override
    public List<OrderView> getOrders() {
        return orders.findAll().stream()
                .map(OrderView::from)
                .toList();
    }

    @Override
    public OrderView getOrder(UUID id) {
        return orders.findById(id)
                .map(OrderView::from)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Override
    public OrderView cancelOrder(UUID id) {
        Order order = orders.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        return OrderView.from(orders.save(order.cancel()));
    }

    @Override
    public void deleteOrder(UUID id) {
        if (!orders.existsById(id)) {
            throw new OrderNotFoundException(id);
        }
        orders.deleteById(id);
    }

    private static OrderLine toOrderLine(ProductView product, int quantity) {
        return new OrderLine(product.id(), product.name(), quantity, product.price());
    }
}
