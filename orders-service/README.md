# Order Service

A Spring Boot microservice for handling order placement in an e-commerce system.

## Features
- Place orders with POST /orders
- Integrates with user-service and rewards-service via Feign
- Persists orders in PostgreSQL (with Flyway migrations)
- Publishes order events to Kafka
- OpenAPI/Swagger documentation
- Robust exception handling and logging
- Unit and integration tests

## Tech Stack
- Java 17+
- Spring Boot 3.2.x+
- Spring Web, Spring Data JPA, OpenFeign, Spring Kafka, Flyway, Lombok, Swagger/OpenAPI
- PostgreSQL
- Kafka

## Getting Started

### Prerequisites
- Java 17+
- Maven 3.8+
- PostgreSQL
- Kafka

### Configuration
Edit `src/main/resources/application.yml` for database, Kafka, and service URLs.

### Database
Create the database:
```
CREATE DATABASE ordersdb;
```
Flyway will auto-migrate the schema on startup.

### Build
```
mvn clean install
```

### Run
```
mvn spring-boot:run
```

### API Docs
Visit [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Test
```
mvn test
```

## Folder Structure
See `src/main/java/com/example/orderservice/` for code organization.

## Endpoints
- `POST /orders` — Place a new order

## License
MIT
