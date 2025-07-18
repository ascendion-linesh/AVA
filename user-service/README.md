# user-service

A Spring Boot microservice for user management in a loyalty rewards system, integrating with Talon.One. Handles registration, authentication, retrieval, update, and deletion of users. Uses PostgreSQL for persistence.

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

## API Endpoints

### Register User
- **POST** `/users`
- **Request Body:**
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "phone": "+1234567890"
}
```
- **Response:**
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
- **GET** `/users/{id}`
- **Response:**
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
- **GET** `/users/email/{email}`
- **Response:** Same as above

### Update User
- **PUT** `/users/{id}`
- **Request Body:**
```json
{
  "name": "Jane Doe",
  "email": "jane@example.com",
  "phone": "+1987654321"
}
```
- **Response:**
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
- **DELETE** `/users/{id}`
- **Response:** `204 No Content`

## Error Handling
- **404 Not Found:**
```json
{"error": "User not found with id: 99"}
```
- **400 Bad Request:**
```json
{"email": "Invalid email format"}
```

## Code Snippets

### Entity
```java
@Entity
@Table(name = "users")
public class User { ... }
```

### Repository
```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
```

### Service
```java
@Service
public class UserService { ... }
```

### Controller
```java
@RestController
@RequestMapping("/users")
public class UserController { ... }
```

### Talon.One Integration
```java
@Component
public class TalonOneClient { ... }
```

## Configuration
See `src/main/resources/application.yml` for PostgreSQL and Talon.One settings.

## Security & Best Practices
- Input validation via DTOs and annotations
- Exception handling via `@ControllerAdvice`
- Secure database access via Spring Data JPA
- Modular, maintainable code

---

**Replace Talon.One and PostgreSQL credentials in `application.yml` before deployment.**
