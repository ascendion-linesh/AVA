# User Service - Deployment & API Documentation

## Deployment Instructions

1. **Configure Database**: 
   - Update `application.yml` with your PostgreSQL credentials and Talon.One API details.
   - Create the database `userdb` in your PostgreSQL instance.

2. **Build & Run**:
   - Build: `mvn clean package`
   - Run: `java -jar target/user-service-0.0.1-SNAPSHOT.jar`

3. **API Docs**:
   - Swagger UI available at: `http://localhost:8080/swagger-ui.html`

## API Endpoints

### Register User
- **POST** `/users`
- **Body**:
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "phone": "+1234567890"
}
```

### Get User by ID
- **GET** `/users/{id}`

### Get User by Email
- **GET** `/users/email/{email}`

### Update User
- **PUT** `/users/{id}`
- **Body**: Same as registration

### Delete User
- **DELETE** `/users/{id}`

## Security
- Input validation on all endpoints
- Error handling with meaningful messages
- Sensitive data not exposed in errors

## Talon.One Integration
- On registration, user is synced with Talon.One using the provided API key and endpoint.

---
