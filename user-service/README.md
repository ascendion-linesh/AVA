# User Service Microservice

A production-ready Spring Boot microservice for user management, integrated with Talon.One and PostgreSQL.

## Features
- User registration, retrieval, update, and deletion
- Integration with Talon.One Campaign Rules Engine
- Input validation, error handling, and security best practices
- PostgreSQL configuration
- RESTful API with Swagger documentation
- Unit tests for service and repository layers

## Folder Structure
```
user-service/
├── src/main/java/com/example/userservice/
│   ├── controller/
│   ├── dto/
│   ├── entity/
│   ├── exception/
│   ├── repository/
│   ├── service/
│   └── talonone/
├── src/main/resources/
│   ├── application.yml
│   └── static/
└── README.md
```

## API Endpoints

### Register User
`POST /users`
```
Request Body:
{
  "name": "John Doe",
  "email": "john@example.com",
  "phone": "1234567890",
  "totalOrders": 5,
  "totalSpent": 100.50
}
Response:
{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "phone": "1234567890",
  "totalOrders": 5,
  "totalSpent": 100.50
}
```

### Get User by ID
`GET /users/{id}`

### Get User by Email
`GET /users/email/{email}`

### Update User
`PUT /users/{id}`

### Delete User
`DELETE /users/{id}`

## Error Handling
- 404 for not found
- 400 for validation errors
- 500 for internal errors

## Talon.One Integration
- On registration, user is also registered in Talon.One.
- Configure Talon.One API URL and Key in `application.yml`.

## Deployment Instructions
1. Ensure PostgreSQL is running and accessible with credentials in `application.yml`.
2. Build and run:
   ```
   mvn clean install
   java -jar target/user-service-1.0.0.jar
   ```
3. Access Swagger UI at `http://localhost:8080/swagger-ui.html`

## Security
- Input validation on all endpoints
- Exception handling for all layers

## Testing
- Unit tests for service and repository layers (see `src/test/java`)

---

**Replace Talon.One credentials and PostgreSQL credentials as needed.**
