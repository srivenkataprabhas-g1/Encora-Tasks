# Retail Project - Microservices with Kafka

## Overview
This Retail Project is a microservices-based application that demonstrates order processing and inventory management using Apache Kafka for asynchronous communication between services. The project consists of two main services: Order Service and Inventory Service, communicating through Kafka topics to handle retail operations efficiently.

## Architecture
The project follows a microservices architecture with event-driven communication:

- **Order Service** (Port 8082): Handles order creation and listens to inventory updates
- **Inventory Service** (Port 8081): Manages inventory operations and processes order-related inventory updates
- **Apache Kafka**: Message broker facilitating asynchronous communication between services

## Project Structure
```
Retail-Project/
├── order-service/
│   ├── src/main/java/com/prabhas/order/
│   │   ├── OrderApplication.java
│   │   └── controller/OrderController.java
│   ├── src/main/resources/
│   │   └── application-1.properties
│   └── pom-1.xml
├── inventory-service/
│   ├── src/main/java/com/inv/
│   │   ├── TestInventory.java
│   │   └── controller/KafkaController.java
│   ├── src/main/resources/
│   │   └── application.properties
│   └── pom-2.xml
└── pom.xml (parent)
```

## Services Details

### Order Service
- **Package**: `com.prabhas.order`
- **Port**: 8082
- **Main Class**: `OrderApplication.java`
- **Controller**: `OrderController.java`
- **Functionality**:
  - Receives order requests via REST API (`POST /orders`)
  - Publishes orders to `order-topic` Kafka topic
  - Listens to `inventory-topic` for inventory update confirmations
  - Uses consumer group `order-group` for message consumption

### Inventory Service
- **Package**: `com.inv`
- **Port**: 8081
- **Main Class**: `TestInventory.java`
- **Controller**: `KafkaController.java`
- **Functionality**:
  - Handles inventory operations via REST API (`POST /inventory`)
  - Publishes inventory updates to `inventory-topic` Kafka topic
  - Processes order-related inventory changes

## Technology Stack
- **Framework**: Spring Boot 2.x
- **Messaging**: Apache Kafka
- **Build Tool**: Maven
- **Language**: Java
- **Architecture**: Microservices
- **Communication**: REST API + Event-driven messaging

## Kafka Configuration

### Topics Used
- `order-topic`: For order-related messages
- `inventory-topic`: For inventory-related messages

### Kafka Properties
- **Bootstrap Servers**: `localhost:9092`
- **Consumer Group**: `order-group`
- **Key/Value Serializers**: StringSerializer/StringDeserializer
- **Auto Offset Reset**: earliest

## Prerequisites
- Java 8 or higher
- Apache Maven 3.6+
- Apache Kafka 2.8+
- Eclipse IDE for Enterprise Java and Web Developers - 2025-06

## Setup Instructions

### 1. Start Kafka
Follow these commands(terminal, path-go to kafka):
- For creation of Cluster ID(UUID)
<pre>.\bin\windows\kafka-storage.bat random-uuid</pre>
- For formatted Storage
<pre>.\bin\windows\kafka-storage.bat format -t <uuid which is generated> -c <location of broker.properties in config folder> </pre>
- For Server Start
<pre>.\bin\windows\kafka-server-start.bat <location of broker.properties></pre>

### 2. Build and Run Services

#### Parent Project
```bash
mvn clean install
```

#### Order Service
```bash
cd order-service
mvn spring-boot:run
# Or run OrderApplication.java from IDE
```

#### Inventory Service
```bash
cd inventory-service  
mvn spring-boot:run
# Or run TestInventory.java from IDE
```

## API Endpoints

### Order Service (Port 8082)
- **POST** `/orders` - Create new order
  ```bash
  curl -X POST http://localhost:8082/orders \
    -H "Content-Type: application/json" \
    -d '{"orderId": "123", "productId": "ABC", "quantity": 5}'
  ```

### Inventory Service (Port 8081)
- **POST** `/inventory` - Send order to inventory topic
  ```bash
  curl -X POST http://localhost:8081/inventory \
    -H "Content-Type: application/json" \
    -d '{"orderId": "123", "productId": "ABC", "quantity": 5}'
  ```

## Message Flow
1. Client creates order via Order Service REST API
2. Order Service publishes order message to `order-topic`
3. Inventory Service processes inventory updates
4. Inventory Service publishes confirmation to `inventory-topic`
5. Order Service receives inventory confirmation and processes accordingly

## Configuration Files

### Order Service (`application-1.properties`)
```properties
server.port=8082
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=order-group
spring.kafka.consumer.auto-offset-reset=earliest
order.topic=order-topic
inventory.topic=inventory-topic
```

### Inventory Service (`application.properties`)
```properties
server.port=8081
spring.application.name=inventory-service
spring.kafka.bootstrap-servers=localhost:9092
inventory.topic=inventory-topic
```

## Troubleshooting
- Ensure Kafka is running before starting services
- Check port availability (8081, 8082, 9092)
- Verify topic creation in Kafka
- Check application logs for connection issues
- Validate JSON format in API requests

## Contributing
1. Fork the repository
2. Create feature branch
3. Commit changes with clear messages
4. Create pull request with detailed description

## License
This project is part of Encora training tasks and follows company guidelines.
