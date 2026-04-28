package com.example.modulith.product.framework.transaction;

import com.example.modulith.product.ProductCatalog;
import com.example.modulith.product.ProductView;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public class TransactionalProductCatalog implements ProductCatalog {

    private final ProductCatalog delegate;

    public TransactionalProductCatalog(ProductCatalog delegate) {
        this.delegate = delegate;
    }

    @Override
    @Transactional(readOnly = true)
    public ProductView getProduct(UUID id) {
        return delegate.getProduct(id);
    }
}
