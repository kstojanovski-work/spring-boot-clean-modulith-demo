# Spring Boot Clean Architecture Modulith Demo

This project combines Spring Modulith module boundaries with Clean Architecture naming and structure inside each module.

The top-level packages are business capabilities, which are the Spring Modulith modules:

```text
com.example.modulith
├── ModulithDemoApplication.java
├── order    # order module
├── product  # product module
└── shared   # tiny shared kernel: Money and DomainException
```

Inside each module, packages follow Clean Architecture terminology:

```text
order
├── entity                  # enterprise business rules
├── usecase                 # application business rules
│   └── gateway             # abstractions required by use cases
├── interfaceadapter        # controllers and API-specific error mapping
└── framework               # Spring Data JPA and technical implementations
```

The root package only contains `ModulithDemoApplication`, the Spring Boot entry point. Shared technical concerns live under `shared`, for example `shared/framework/time/ClockConfig` and `shared/interfaceadapter/web/ApiErrorHandler`.

## Key Points

Entities and use cases are framework-free: no Spring stereotypes and no transaction annotations in `entity` or `usecase`.

`OrderUseCase` depends on gateway interfaces such as `OrderRepository` and `OrderNumberGenerator`, not directly on Spring Data JPA.

Framework configuration performs the explicit wiring:

```text
order/framework/OrderUseCaseConfiguration.java
order/framework/OrderGatewayConfiguration.java
product/framework/ProductUseCaseConfiguration.java
product/framework/ProductGatewayConfiguration.java
```

Transactions are applied in framework decorators such as `TransactionalOrderUseCase` and `TransactionalProductCatalog`, not inside the use cases themselves.

`UuidOrderNumberGenerator` is a framework implementation inside the `order` module because order number generation is an implementation detail of that business capability:

```text
src/main/java/com/example/modulith/order/framework/number/UuidOrderNumberGenerator.java
```

The `order` module calls the public API of the `product` module through `ProductCatalog`, but it does not reach into product application or persistence internals.

## Run It

```bash
mvn spring-boot:run
```

Open the browser UI:

```text
http://localhost:8080
```

Create an order:

```bash
curl -i -X POST http://localhost:8080/orders \
  -H 'Content-Type: application/json' \
  -d '{
    "customerEmail": "learner@example.com",
    "items": [
      {
        "productId": "11111111-1111-1111-1111-111111111111",
        "quantity": 2
      }
    ]
  }'
```

List created orders:

```bash
curl http://localhost:8080/orders
```

Fetch a product:

```bash
curl http://localhost:8080/products/11111111-1111-1111-1111-111111111111
```

## Test It

```bash
mvn test
```

`ModulithStructureTest` asks Spring Modulith to verify that modules only depend on exposed module APIs.
