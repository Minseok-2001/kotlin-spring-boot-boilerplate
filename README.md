# DDD Kotlin Spring Boot Boilerplate

This repository provides a boilerplate implementation of a backend service using Kotlin, Spring Boot, and a
Domain-Driven Design (DDD) layered architecture. It is designed to offer a robust starting point for projects requiring
JWT-based security, OAuth2 social login, and a clean separation of responsibilities.

---

## Key Features

### Architectural Pattern: DDD Layered Architecture

- **Presentation Layer**: Handles HTTP requests via REST controllers and manages data transfer objects (DTOs).
- **Application Layer**: Manages use cases and service orchestration without containing core business logic.
- **Domain Layer**: Contains business logic, domain models, and interfaces (e.g., repositories).
- **Infrastructure Layer**: Handles technical implementations like persistence (JPA), security (JWT), and external
  integrations (e.g., OAuth).

### Tech Stack

- **Kotlin**
- **Spring Boot**
- **PostgreSQL**: Relational database for persistence.
- **Flyway**: Manages database schema migrations.
- **Spring Security**:
    - OAuth2 integration for social login (Google, Apple).
    - JWT-based authentication and stateless session management.
- **Springdoc-OpenAPI**:
    - Auto-generates Swagger API documentation for REST endpoints.

### Security Features

- **JWT Authentication**: Stateless token-based authentication with refresh token support.
- **OAuth2 Social Login**: Easily extendable to support additional providers.
- **Custom Security Configurations**:
    - `JwtAuthenticationFilter` for handling token validation.
    - `CurrentUser` annotation for injecting the authenticated user into controller methods.

---

## How to Use

### Prerequisites

- **JDK 21** or higher.
- **PostgreSQL**: Ensure the database is running.
- **Docker**: (Optional) for running a containerized environment.

### Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo/kotlin-spring-boot-boilerplate.git
   cd kotlin-spring-boot-boilerplate
   ```
2. Configure the database in `src/main/resources/application.yml`:
   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/your_database
       username: your_username
       password: your_password
     jpa:
       hibernate:
         ddl-auto: validate
     flyway:
       enabled: true
   ```
3. Run the application:
   ```bash
   ./gradlew bootRun
   ```

4. Access Swagger UI at [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).

---

## Testing

- **Unit Tests**: Located in `src/test/kotlin/dev/kotlinspringbootboilerplate/`.
- **Integration Tests**: Utilizes Testcontainers for database integration.
- Run all tests:
  ```bash
  ./gradlew test
  ```

---

## Customization

- Add new OAuth providers by extending the `OAuthUserInfo` interface.
- Modify security rules in `SecurityConfig.kt`.
- Add database migrations in `src/main/resources/db/migration/`.

---