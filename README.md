# **Restaurant Application**

The Restaurant App is a microservices-based system for automating ordering and food preparation for restaurants
Application responsible for managing the lifecycle of customer's orders and cooking process.

## **Architecture & Features**

**Microservices:** Spring Cloud API Gateway was implemented to the application as a single entrypoint. Also in the API Gateway level was configured centralized security-layer (JWT-authentication).

**Event-Driven Communication:** Order-service publishes OrderCreatedEvent to Kafka. Kitchen-service consumes the event and initiates the cooking process. Once prepared, Kitchen-service publishes an OrderReadyEvent. Order-service listens for completion to update the final order status.

**Transactional Outbox Pattern:** ensures reliable message delivery by saving events in the database before publishing to Kafka.

## **Running the Service**
1. Clone the repository:
`git clone https://github.com/pilipuk-aliaksandr/restaurant-app.git`

2. Start infrastructure via Docker:
`docker-compose up -d`

3. Build maven and run all microservices:
api-gateway (_Port:8082_),
kitchen-service (_Port:8081_),
order-service (_Port:8080_).

### **API Endpoints**

When the services are running, you can access the Swagger UI for each service:
http://localhost:<microservice_port>/swagger-ui/index.html