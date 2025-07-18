# User Service for Loyalty Rewards System

This Spring Boot microservice manages user registration, retrieval, update, and deletion for a loyalty rewards system, with integration to Talon.One for campaign management.

## Features
- User registration, retrieval by ID/email, update, and deletion
- Talon.One integration during registration
- PostgreSQL persistence
- Input validation, error handling, and security best practices
- OpenAPI (Swagger) documentation
- Unit tests for service and repository layers

## API Endpoints

### Register User
- **POST /users**
- Request Body:
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "phone": "+1234567890"
}
```
- Response (201):
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "phone": "+1234567890",
  "totalOrders": 0,
  "totalSpent": 0.0
}
```

### Get User by ID
- **GET /users/{id}**
- Response (200):
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "phone": "+1234567890",
  "totalOrders": 0,
  "totalSpent": 0.0
}
```

### Get User by Email
- **GET /users/email/{email}**
- Response (200):
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "phone": "+1234567890",
  "totalOrders": 0,
  "totalSpent": 0.0
}
```

### Update User
- **PUT /users/{id}**
- Request Body:
```json
{
  "name": "Jane Doe",
  "email": "jane@example.com",
  "phone": "+1987654321"
}
```
- Response (200):
```json
{
  "id": 1,
  "name": "Jane Doe",
  "email": "jane@example.com",
  "phone": "+1987654321",
  "totalOrders": 0,
  "totalSpent": 0.0
}
```

### Delete User
- **DELETE /users/{id}**
- Response: 204 No Content

## Error Responses
- 400 Bad Request: Invalid input or duplicate email
- 404 Not Found: User not found
- 500 Internal Server Error: Unexpected errors

## Running the Service
1. Configure PostgreSQL and Talon.One properties in `src/main/resources/application.yml`.
2. Build and run the application:
   ```sh
   mvn clean install
   mvn spring-boot:run
   ```
3. Access Swagger UI at `http://localhost:8080/swagger-ui.html` for API testing.

## Folder Structure
```
user-service/
  src/main/java/com/example/userservice/
    controller/
    dto/
    entity/
    exception/
    repository/
    service/
    talonone/
  src/main/resources/
    application.yml
    static/
```

## Security
- Input validation via DTOs and validation annotations
- Secure database access via Spring Data JPA
- Sensitive data is not exposed in API responses

## Testing
- Unit tests for service and repository layers are provided.

---
For questions, contact the engineering team.
