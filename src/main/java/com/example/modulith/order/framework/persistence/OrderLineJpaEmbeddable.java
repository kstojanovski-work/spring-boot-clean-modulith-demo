package com.example.modulith.order.framework.persistence;

import com.example.modulith.order.entity.OrderLine;
import com.example.modulith.shared.Money;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

@Embeddable
class OrderLineJpaEmbeddable {

    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "product_name")
    private String productName;

    private int quantity;

    @Column(name = "unit_price_amount")
    private BigDecimal unitPriceAmount;

    @Column(name = "unit_price_currency")
    private String unitPriceCurrency;

    protected OrderLineJpaEmbeddable() {
    }

    private OrderLineJpaEmbeddable(UUID productId, String productName, int quantity, BigDecimal unitPriceAmount, String unitPriceCurrency) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPriceAmount = unitPriceAmount;
        this.unitPriceCurrency = unitPriceCurrency;
    }

    static OrderLineJpaEmbeddable from(OrderLine line) {
        return new OrderLineJpaEmbeddable(
                line.productId(),
                line.productName(),
                line.quantity(),
                line.unitPrice().amount(),
                line.unitPrice().currency().getCurrencyCode()
        );
    }

    OrderLine toDomain() {
        return new OrderLine(productId, productName, quantity, new Money(unitPriceAmount, Currency.getInstance(unitPriceCurrency)));
    }

}
