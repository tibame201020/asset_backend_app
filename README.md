# Asset Backend App (Spring Boot 3 Upgrade)

This project has been refactored from Spring Boot 2 (Java 8) to **Spring Boot 3 (JDK 21)**.

## Key Changes
- **Spring Boot 3**: Upgraded to Spring Boot 3.2.2.
- **Java 21**: Project now requires JDK 21.
- **Jakarta EE**: Migrated from `javax.*` to `jakarta.*` persistence packages.
- **Virtual Threads**: Enabled virtual threads for improved concurrency (`spring.threads.virtual.enabled=true`).

## API Documentation
Detailed API endpoints and data structures are documented in [endpoints.md](endpoints.md).

## How to Run

### Prerequisites
- Java 21 SDK installed.
- Maven installed (optional if using wrapper, but recommended).

### Run Application
```bash
mvn clean spring-boot:run
```

The application uses an in-memory H2 database.
**H2 Console**: http://localhost:9889/h2-console
- JDBC URL: `jdbc:h2:./h2data/test`
- User: `user`
- Password: `pwd`

## Configuration
Configuration is located in `src/main/resources/application.properties`.
Virtual threads are enabled by default:
```properties
spring.threads.virtual.enabled=true
```
