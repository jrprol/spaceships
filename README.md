# Spaceship API

## Overview

The Spaceship API is a Spring Boot application designed to manage a collection of spaceships. It includes CRUD operations for spaceships, integrates with a Kafka messaging system, and uses H2 in-memory database for data storage. The API is secured with JWT and documented using Springdoc OpenAPI.

## Features

- CRUD operations for managing spaceships.
- Integration with Apache Kafka for message processing.
- In-memory H2 database for data storage.
- Flyway for database migrations.
- JWT-based authentication and authorization.
- Swagger UI for API documentation.
- Docker support for easy setup and deployment.

## Prerequisites

- Java 17
- Spring.boot 3.0+
- Maven
- Docker (for running Docker containers)

## Technologies Used

- Spring Boot 3.0.6
- Spring Data JPA
- Spring Security
- Spring Kafka
- H2 Database
- Flyway
- Springdoc OpenAPI (Swagger)
- JWT (JSON Web Tokens)

## Construction
To build the project execute:
```bash
mvn clean install

## Kafka
- Configure topic:
	- kafka-topics.sh --create --topic your-topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1

- Test consumer:
	- kafka-console-producer.sh --topic your-topic --bootstrap-server localhost:9092

Note: If don't have a kafka in your computer, for not to constantly get warnings in the log, comment these lines:
- lines 3 and 14 in class /spaceships/src/main/java/com/jrpg/service/ConsumerService.java
- Line 61 in class /spaceships/src/test/java/com/jrpg/integration/KafkaConsumerIntegrationTest.java


## Swagger
- Swagger UI: 
	- http://localhost:8080/swagger-ui/index.html

- API Documentation:
	- http://localhost:8080/v3/api-docs

## Use API
- http://localhost:8080/api/spaceships
- usr: "user"
- pwd: "password"
