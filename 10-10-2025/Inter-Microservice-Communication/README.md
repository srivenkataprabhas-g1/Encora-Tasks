# Project: Employee and Order Microservices

## Overview
This project demonstrates a **microservices architecture** built with Spring Boot. It consists of two independent services: **Employee Service** and **Order Service**, each backed by 'swag' MySQL database. Services communicate via REST APIs using `RestTemplate`.

## Services

### 1. Employee Service
- **Port:** 8083
- **Responsibilities:**
  - Manage Employee entities (CRUD operations).
  - Expose endpoints to retrieve employee details and associated orders from Order Service.
- **Database:** MySQL (`swag`)
- **Key Components:**
  - `Employee` JPA entity (`model/Employee.java`)
  - `EmployeeRepository` interface (`repository/EmployeeRepository.java`)
  - `EmployeeService` business logic (`service/EmployeeService.java`)
  - `EmployeeController` REST endpoints (`controller/EmployeeController.java`)
  - `DataInitializer` seeds sample employees (`config/DataInitializer.java`)
- **Endpoints:**
  - `GET /employee` – List all employees
  - `GET /employee/{id}` – Get employee by ID
  - `POST /employee` – Create employee
  - `PUT /employee/{id}` – Update employee
  - `DELETE /employee/{id}` – Delete employee
  - `GET /employee/{id}/with-orders` – Employee with their orders

### 2. Order Service
- **Port:** 8081
- **Responsibilities:**
  - Manage Order entities (CRUD operations).
  - Expose endpoints to retrieve order details and associated employee data from Employee Service.
- **Database:** MySQL (`swag`)
- **Key Components:**
  - `Order` JPA entity (`model/Order.java`)
  - `OrderRepository` interface (`repository/OrderRepository.java`)
  - `OrderService` business logic (`service/OrderService.java`)
  - `OrderController` REST endpoints (`controller/OrderController.java`)
  - `DataInitializer` seeds sample orders (`config/DataInitializer.java`)
- **Endpoints:**
  - `GET /order` – List all orders
  - `GET /order/{id}` – Get order by ID
  - `POST /order` – Create order
  - `PUT /order/{id}` – Update order
  - `DELETE /order/{id}` – Delete order
  - `GET /order/employee/{employeeId}` – Orders by employee
  - `GET /order/{id}/with-employee` – Order with employee details

## Database Configuration
Both services use MySQL. Update `application.properties` and `application-1.properties`:

```properties
# Common
spring.datasource.username=root
spring.datasource.password=tiger
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Employee Service
spring.datasource.url=jdbc:mysql://localhost:3306/swag?createDatabaseIfNotExist=true&useSSL=false
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Order Service
spring.datasource.url=jdbc:mysql://localhost:3306/swag?createDatabaseIfNotExist=true&useSSL=false
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

Ensure MySQL server is running and credentials match.

## Maven Dependencies
Add MySQL connector to each service's `pom.xml`:
```xml
<dependency>
  <groupId>mysql</groupId>
  <artifactId>mysql-connector-java</artifactId>
  <scope>runtime</scope>
</dependency>
```

Other dependencies remain unchanged (Spring Web, Data JPA, Lombok, DevTools, OpenAPI).

## Build and Run
1. **Ensure MySQL is running** and database `swag` exist or will be auto-created.
2. **Configure** credentials in properties files.
3. **Build**:
   ```bash
   mvn clean install
   ```
4. **Run** both services:
   ```bash
   mvn spring-boot:run
   ```

## Inter-Service Communication
Employee Service and Order Service communicate over REST using `RestTemplate`.

## API Documentation
Swagger UI at `http://localhost:{port}/swagger-ui.html`.
