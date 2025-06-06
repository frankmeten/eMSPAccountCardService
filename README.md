# eMSP Account & Card Service

This project implements a RESTful API for managing Accounts and Cards for an eMSP (e-Mobility Service Provider) system.

## Features
- Create, activate, deactivate Accounts
- Create, assign, activate, deactivate Cards
- Query Accounts & Cards by last_updated with pagination
- Data validation and format checking
- RDBMS integration (JPA/Hibernate)
- Dockerfile for containerization
- Unit tests
- GitHub Actions CI pipeline
- Domain-Driven Design

## Getting Started

### Prerequisites
- Java 17+
- Maven 3.6+
- Docker (for containerization)

### Build & Run
```bash
cd eMSPAccountCardService
mvn clean install
mvn spring-boot:run
```

### Run Tests
```bash
mvn test
```

### Build Docker Image
```bash
docker build -t emsp-account-card-service .
```

## Project Structure
- `src/main/java/com/example/emsp/domain` - Domain models
- `src/main/java/com/example/emsp/repository` - JPA repositories
- `src/main/java/com/example/emsp/service` - Business logic
- `src/main/java/com/example/emsp/controller` - REST controllers
- `src/test/java/com/example/emsp` - Unit tests

## License
Apache 2.0
