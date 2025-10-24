# Order Processing Service (API Reference)

**Backend system in Java that handles order processing efficiently.**  
Repository: `sid1097/peerisland-order-processing-service-demo`.

---

## Table of contents
- Overview
- Run locally
- Postman collection
- API summary (paths, methods)
- Models (request / response)
- Example OpenAPI snippet

---

## Overview
This service provides a lightweight order-management API for creating, querying, updating and processing e-commerce orders. The repository contains a Postman collection that demonstrates the API flows.

---

## Run locally
> Minimal steps — adjust if your project uses a specific JDK/Maven version.

```bash
# clone
git clone https://github.com/sid1097/peerisland-order-processing-service-demo.git
cd peerisland-order-processing-service-demo

# build & run using Maven wrapper (if present)
./mvnw clean package
./mvnw spring-boot:run

# or run the built jar
java -jar target/peerisland-order-processing-service-demo-<version>.jar
```

If your project uses Spring Boot, it will usually expose endpoints under `http://localhost:8990`.

---

## Postman collection
A Postman collection `Ecommerce_Order_API_Collection.postman_collection.json` is included in the repo — import it into Postman to try the APIs and example payloads.

---

## Base URL
`http://localhost:8990/api` (example — replace port/path if different in your app config)

---

## API Summary (Swagger-style)

> The endpoints below are a clean, swagger-like presentation you can paste into an `api-docs` file. They follow common order-processing patterns — adjust names/paths to match your controllers if needed.

### 1. Create Order
`POST /api/v1/orders`

**Description:** Create a new order.

**Request Body (application/json):**

```json
{
  "customerId": "cust-003",
  "items": [
    {
      "productId": "SKU-003",
      "name": "Monitor",
      "quantity": 3,
      "unitPrice": 7999.0
    },
    {
      "productId": "SKU-005",
      "name": "Amplifier",
      "quantity": 1,
      "unitPrice": 3999.5
    }
  ]
}
```

**Responses:**
- `201 Created` — returns created `Order` resource (see Models).
- `400 Bad Request` — validation errors.

---

### 2. Get Order by ID
`GET /api/v1/orders/{orderId}`

**Path parameter:**
- `orderId` — order identifier

**Responses:**
- `200 OK` — order JSON
- `404 Not Found` — order not found

---

### 3. List Orders
`GET /api/v1/orders`

**Query params (optional):**
- `status` — PENDING / PROCESSING / SHIPPED / DELIVERED / CANCELLED
- `page` — page number
- `size` — page size

**Response:**
- `200 OK` — paginated list of orders

---

### 4. Update Order Status
`PATCH /api/v1/orders/{orderId}/status`

**Request Body:**

```json
{
  "status": "SHIPPED"
}
```

**Responses:**
- `200 OK` — updated order
- `404 Not Found` — no such order

---

### 5. Cancel Order
`DELETE /api/v1/orders/{orderId}/cancel`

**Description:** Cancel an order if allowed by business rules.

**Response:**
- `200 OK` — order cancelled
- `400 Bad Request` — cannot cancel

---


## Models

### Order (example)
```json
{
  "orderId": "ORD-1001",
  "customerId": "CUST-100",
  "items": [
    {
      "sku": "SKU-123",
      "name": "Widget A",
      "quantity": 2,
      "unitPrice": 199.99
    }
  ],
  "status": "PENDING",
  "totalAmount": 399.98,
  "currency": "INR",
  "createdAt": "2025-10-24T12:34:56Z",
  "updatedAt": "2025-10-24T12:34:56Z"
}
```

### Item
```json
{
  "sku": "SKU-123",
  "name": "Widget A",
  "quantity": 2,
  "unitPrice": 199.99
}
```

---

## Example OpenAPI (YAML) snippet
You can paste this into an OpenAPI file and expand it to generate Swagger UI via `springdoc-openapi` or `swagger-ui`.

```yaml
---
openapi: 3.1.0
info:
  title: Orders Processing API
  description: E-commerce Order Processing API
  contact:
    name: Sk Sajid Hassan
    email: hassansajid718@gmail.com
  version: 1.0.0
servers:
- url: http://localhost:8990
  description: Generated server url
tags:
- name: Orders
  description: Order management APIs
paths:
  "/api/v1/orders":
    get:
      tags:
      - Orders
      summary: Get List of Orders
      description: Get list of existing orders, optionally by order status
      operationId: list
      parameters:
      - name: status
        in: query
        required: false
        schema:
          type: string
          enum:
          - PENDING
          - PROCESSING
          - SHIPPED
          - DELIVERED
          - CANCELED
      - name: page
        in: query
        required: false
        schema:
          type: integer
          format: int32
          default: 0
      - name: size
        in: query
        required: false
        schema:
          type: integer
          format: int32
          default: 20
      responses:
        '200':
          description: OK
          content:
            "*/*":
              schema:
                "$ref": "#/components/schemas/PagedModelOrderResponse"
        '400':
          description: Bad Request
          content:
            "*/*":
              schema:
                type: object
                additionalProperties: {}
        '404':
          description: Not Found
          content:
            "*/*":
              schema:
                type: object
                additionalProperties: {}
    post:
      tags:
      - Orders
      summary: Create an order
      description: Create a new order with multiple items
      operationId: create
      requestBody:
        content:
          application/json:
            schema:
              "$ref": "#/components/schemas/CreateOrderRequest"
        required: true
      responses:
        '200':
          description: OK
          content:
            "*/*":
              schema:
                "$ref": "#/components/schemas/OrderResponse"
        '400':
          description: Bad Request
          content:
            "*/*":
              schema:
                type: object
                additionalProperties: {}
        '404':
          description: Not Found
          content:
            "*/*":
              schema:
                type: object
                additionalProperties: {}
  "/api/v1/orders/{id}/status":
    patch:
      tags:
      - Orders
      summary: Update an order
      description: Update an existing order with multiple items
      operationId: updateStatus
      parameters:
      - name: id
        in: path
        required: true
        schema:
          type: string
          format: uuid
      requestBody:
        content:
          application/json:
            schema:
              "$ref": "#/components/schemas/UpdateStatusRequest"
        required: true
      responses:
        '200':
          description: OK
          content:
            "*/*":
              schema:
                "$ref": "#/components/schemas/OrderResponse"
        '400':
          description: Bad Request
          content:
            "*/*":
              schema:
                type: object
                additionalProperties: {}
        '404':
          description: Not Found
          content:
            "*/*":
              schema:
                type: object
                additionalProperties: {}
  "/api/v1/orders/{id}":
    get:
      tags:
      - Orders
      summary: Get an order
      description: Get a existing order by order id
      operationId: get
      parameters:
      - name: id
        in: path
        required: true
        schema:
          type: string
          format: uuid
      responses:
        '200':
          description: OK
          content:
            "*/*":
              schema:
                "$ref": "#/components/schemas/OrderResponse"
        '400':
          description: Bad Request
          content:
            "*/*":
              schema:
                type: object
                additionalProperties: {}
        '404':
          description: Not Found
          content:
            "*/*":
              schema:
                type: object
                additionalProperties: {}
    delete:
      tags:
      - Orders
      summary: Delete an order
      description: Delete an existing order with multiple items
      operationId: cancel
      parameters:
      - name: id
        in: path
        required: true
        schema:
          type: string
          format: uuid
      responses:
        '200':
          description: OK
        '400':
          description: Bad Request
          content:
            "*/*":
              schema:
                type: object
                additionalProperties: {}
        '404':
          description: Not Found
          content:
            "*/*":
              schema:
                type: object
                additionalProperties: {}
components:
  schemas:
    CreateOrderRequest:
      type: object
      properties:
        customerId:
          type: string
          minLength: 1
        items:
          type: array
          items:
            "$ref": "#/components/schemas/OrderItemRequest"
          maxItems: 2147483647
          minItems: 1
      required:
      - customerId
    OrderItemRequest:
      type: object
      properties:
        productId:
          type: string
          minLength: 1
        name:
          type: string
          minLength: 1
        quantity:
          type: integer
          format: int32
        unitPrice:
          type: number
      required:
      - name
      - productId
    OrderItemResponse:
      type: object
      properties:
        id:
          type: integer
          format: int64
        productId:
          type: string
        name:
          type: string
        quantity:
          type: integer
          format: int32
        unitPrice:
          type: number
        lineTotal:
          type: number
    OrderResponse:
      type: object
      properties:
        id:
          type: string
          format: uuid
        customerId:
          type: string
        status:
          type: string
          enum:
          - PENDING
          - PROCESSING
          - SHIPPED
          - DELIVERED
          - CANCELED
        items:
          type: array
          items:
            "$ref": "#/components/schemas/OrderItemResponse"
        totalAmount:
          type: number
        createdAt:
          type: string
          format: date-time
        updatedAt:
          type: string
          format: date-time
    UpdateStatusRequest:
      type: object
      properties:
        status:
          type: string
          enum:
          - PENDING
          - PROCESSING
          - SHIPPED
          - DELIVERED
          - CANCELED
      required:
      - status
    PageMetadata:
      type: object
      properties:
        size:
          type: integer
          format: int64
        number:
          type: integer
          format: int64
        totalElements:
          type: integer
          format: int64
        totalPages:
          type: integer
          format: int64
    PagedModelOrderResponse:
      type: object
      properties:
        content:
          type: array
          items:
            "$ref": "#/components/schemas/OrderResponse"
        page:
          "$ref": "#/components/schemas/PageMetadata"

```

