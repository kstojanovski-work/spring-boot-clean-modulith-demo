package com.example.modulith.order.interfaceadapter.web;

import com.example.modulith.order.usecase.OrderInputBoundary;
import com.example.modulith.order.usecase.OrderView;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
class OrderController {

    private final OrderInputBoundary orders;

    OrderController(OrderInputBoundary orders) {
        this.orders = orders;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    OrderView placeOrder(@Valid @RequestBody PlaceOrderHttpRequest request) {
        return orders.placeOrder(request.toUseCaseRequest());
    }

    @GetMapping
    List<OrderView> getOrders() {
        return orders.getOrders();
    }

    @GetMapping("/{id}")
    OrderView getOrder(@PathVariable UUID id) {
        return orders.getOrder(id);
    }

    @PostMapping("/{id}/cancel")
    OrderView cancelOrder(@PathVariable UUID id) {
        return orders.cancelOrder(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteOrder(@PathVariable UUID id) {
        orders.deleteOrder(id);
    }
}
