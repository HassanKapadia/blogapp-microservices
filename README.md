# Blogging App using Spring Boot (Microservices)

A full-stack blogging application built using **Spring Boot microservices**, where users can write articles and post comments.  
The system follows a clean microservices architecture with **service discovery, API gateway routing, and isolated databases per service**.

---

## Requirements

* JDK 21
* Gradle 8.14.3
* Docker or Podman
* Docker Compose or Podman Compose

> No local PostgreSQL installation is required. Databases run in containers.

---

## Technical Specifications

This project is built entirely on the **Spring Boot & Spring Cloud ecosystem** with a **React (Vite)** frontend.

Key technologies and features include:

* Spring Boot – Microservices development
* Spring Cloud Eureka – Service discovery
* Spring Cloud Gateway – Central API routing
* Spring Cloud OpenFeign – Inter-service communication
* JWT-based Authentication – Secure access
* Spring Security + Password Encryption
* Spring Data JPA (Hibernate)
* PostgreSQL – Separate DB per service
* Gradle – Build and dependency management
* React + Vite – Frontend
* Docker / Podman – Containerization

---

## Microservices Overview

1. **Eureka Server**
    * Service discovery and registration

2. **API Gateway**
    * Single entry point for all backend services

3. **Auth Service**
    * User authentication and JWT generation

4. **User Service**
    * User account management

5. **Article Service**
    * Create, update, and manage blog articles

6. **Comment Service**
    * Add and manage comments on articles

7. **blogapp-common**
    * Shared DTOs, constants, and utilities

---

## Functional Overview

1. **User Registration & Login**
    * Users can sign up and log in securely.
    * JWT token issued on successful authentication.

2. **Article Management**
    * Users can create, update, and delete articles.
    * Articles from all authors are listed.

3. **Comments**
    * Users can view, add, edit, and delete comments on articles.

4. **Account Management**
    * View or update personal details.

5. **Logout**
    * Secure logout using JWT invalidation.

---

## Database Design

Each service has its **own PostgreSQL database**:

* `userdb` – User Service
* `articledb` – Article Service
* `commentdb` – Comment Service

Databases are:
* Automatically created
* Persisted using Docker volumes
* Isolated per microservice

---

## Installation & Setup

### Step 1: Clone the repository

```bash
git clone git@github.com:HassanKapadia/blogapp-microservices.git
cd blogapp-microservices
```

### Step 2: Build the project
```bash
./gradlew clean build
```
### Step 3: Start the application
```bash
docker compose up --build
```

## Access the Application
* Frontend: [http://localhost:5173](http://localhost:5173)
* API Gateway: [http://localhost:8080](http://localhost:8080)
* Eureka Dashboard: [http://localhost:8761](http://localhost:8761)

## Author
Hassan Kapadia
