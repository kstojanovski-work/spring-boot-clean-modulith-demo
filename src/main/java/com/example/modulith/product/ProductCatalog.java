package com.example.modulith.product;

import java.util.UUID;

public interface ProductCatalog {

    ProductView getProduct(UUID id);
}
