## Rest API

Build REST API with Spring Boot, Spring Data JPA and MariaDB

## Requirements

1. Java - 11.x.x

2. Gradle - 6.x.x

## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/diogo09git/rest-api-with-springDataJPA.git
```
**2. Create MariaDB database and change username and password as per your installation**

```bash
  create database productdb
  change spring.datasource.username and spirng.datasource.password
```

**3. Package your app and run using gradle**

```bash
.gradlew build
java -jar build/libs/api-rest-0.0.1-SNAPSHOT.jar
```

**4. Alternatively, you can run the app without packaging it using**

```bash
./gradlew bootRun
```

The app will start running at <http://localhost:8080>.

## Explore Rest APIs

The app defines following CRUD APIs.

    GET /api/products
    
    GET /api/products/{id}
    
    POST /api/products
    
    DELETE /api/products/{id}
    
    PATCH /api/products/{id}

#### You can test them using Postman or any other rest client.
