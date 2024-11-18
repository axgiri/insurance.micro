# insurance.micro

This project is a microservice-based application that uses Kafka, Redis, and Spring Boot to manage insurance policies and generate documents.

---

## Table of Contents
1. [Introduction](#introduction)
2. [Project Structure](#project-structure)
3. [Technologies](#technologies)
4. [Installation](#installation)
5. [Running the Application](#running-the-application)
6. [Service Descriptions](#service-descriptions)
7. [Postman Collection](#postman-collection)

---

## Introduction

This application facilitates insurance policy management, allowing users to purchase policies and generate PDF documents. Each microservice operates independently, with communication between them handled via HTTP and Kafka.

---

## Project Structure

```plaintext
INSURANCE/
├── authentication-service/
├── document-service/
├── gateway/
├── insure-policy/
├── purchase-service/
├── docker-compose.yml
├── pom.xml
├── postman_collection.json
├── README.md
```

---

## Technologies

- **Spring Boot** - for building microservices.
- **Kafka** - for inter-service communication.
- **Redis** - for caching.
- **PostgreSQL** - for database management.
- **Docker** - for containerization and orchestration.
- **iText PDF** - for generating PDF documents.

---

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/axgiri/insurance.micro
   cd insurance.micro
   ```
2. Ensure the following dependencies are installed:
   - Docker
   - JDK 17
   - Maven
3. Configure environment variables in `application.properties` (if applicable). Example:
   ```plaintext
   SPRING_DATASOURCE_URL=jdbc:postgresql://policy-db:5432/policydb
   SPRING_DATASOURCE_USERNAME=postgres
   SPRING_DATASOURCE_PASSWORD=1
   ```

---

## Running the Application

1. Build the project using Maven:
   ```bash
   ./mvnw clean install
   ```
2. Start all services using Docker Compose:
   ```bash
   docker-compose up --build
   ```
3. The application will be accessible at the following URLs:
   - Gateway: `http://localhost:8080`
   - Authentication Service: `http://localhost:8081`
   - Policy Service: `http://localhost:8082`
   - Purchase Service: `http://localhost:8083`
   - Document Service: `http://localhost:8084`

---

## Service Descriptions

### 1. Gateway
- Responsible for routing requests between the client and microservices.
- URL: `http://localhost:8080`

### 2. Authentication Service
- Handles user registration and authentication.
- URL: `http://localhost:8081`

### 3. Policy Service
- Manages insurance policies (CRUD operations).
- URL: `http://localhost:8082`

### 4. Purchase Service
- Processes policy purchases.
- URL: `http://localhost:8083`

### 5. Document Service
- Generates PDF documents for policies.
- URL: `http://localhost:8084`

---

## Postman Collection

You can download the Postman collection for testing the API [here](postman_collection.json).

---
