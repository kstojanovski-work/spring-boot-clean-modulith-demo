package com.example.modulith.product.framework.persistence;

import com.example.modulith.product.ProductView;
import com.example.modulith.shared.Money;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

@Entity
@Table(name = "products")
class ProductJpaEntity {

    @Id
    private UUID id;

    private String name;

    @Column(name = "price_amount")
    private BigDecimal priceAmount;

    @Column(name = "price_currency")
    private String priceCurrency;

    protected ProductJpaEntity() {
    }

    ProductView toView() {
        return new ProductView(id, name, new Money(priceAmount, Currency.getInstance(priceCurrency)));
    }
}
