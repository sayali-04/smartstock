# SmartStock — Intelligent Inventory Management System

SmartStock is a backend system that helps small retailers and home kitchens track inventory, reduce food/product wastage, and get automated alerts before stock expires. Unlike simple inventory trackers, it manages stock at the **batch level** and uses **FIFO (First-In-First-Out) logic** to ensure older stock is used before newer stock — minimizing waste in a way a single running-total quantity field never could.

## Problem It Solves

Small businesses often lose money because:
- Expired stock isn't caught until it's too late
- Staff use newer stock while older stock quietly expires in the back
- Reordering happens reactively, only after running out

SmartStock addresses all three with batch tracking, automated expiry alerts, and role-based access to keep the system reliable in daily use.

## Tech Stack

- **Backend:** Java 21, Spring Boot 3.x (Spring Web, Spring Data JPA, Spring Security, Spring Scheduler)
- **Database:** MySQL, Hibernate ORM
- **Build Tool:** Maven
- **Testing:** Postman
- **Version Control:** Git, GitHub

## Key Features

### 1. Batch-Wise Inventory Tracking
Every stock delivery is tracked as its own batch with its own expiry date, instead of one blended quantity per product.

### 2. FIFO Stock Deduction
When stock is used or sold, the system automatically deducts from the batch expiring soonest first — a small algorithmic feature that mirrors real warehouse practice.

### 3. Automated Expiry Alerts
A scheduled background job (`@Scheduled`) checks daily for batches nearing expiry and raises alerts automatically, with a manual trigger endpoint for testing.

### 4. Role-Based Access Control
Spring Security with BCrypt password hashing distinguishes Admin (full access) from Staff (restricted access) — for example, only Admins can add new stock batches.

## Architecture
Client (Postman / Frontend)
↓ REST APIs
Controller Layer   → handles HTTP requests
Service Layer      → business logic (FIFO, alerts, security)
Repository Layer   → Spring Data JPA, talks to MySQL
↓
MySQL Database
## API Endpoints

| Method | Endpoint | Description | Access |
|--------|----------|--------------|--------|
| POST | `/api/products` | Add a new product | Admin, Staff |
| GET | `/api/products` | List all products | Admin, Staff |
| GET | `/api/products/{id}` | Get product by ID | Admin, Staff |
| PUT | `/api/products/{id}` | Update product | Admin, Staff |
| DELETE | `/api/products/{id}` | Delete product | Admin, Staff |
| POST | `/api/stock/add` | Add a new stock batch | Admin only |
| POST | `/api/stock/deduct` | Deduct stock (FIFO logic) | Admin, Staff |
| GET | `/api/stock/product/{id}` | View active batches for a product | Admin, Staff |
| GET | `/api/alerts` | View unresolved alerts | Admin, Staff |
| POST | `/api/alerts/run-check` | Manually trigger expiry check | Admin, Staff |

## Setup Instructions

1. Clone the repository:git clone https://github.com/sayali-04/smartstock.git
2. Create a MySQL database:
```sql CREATE DATABASE smartstock_db;
```
3. Update `src/main/resources/application.properties` with your MySQL username/password
4. Run the application:mvn spring-boot:run
5. Default admin login: `username: admin`, `password: admin123`

## Future Improvements
- JWT-based authentication instead of HTTP Basic Auth
- Frontend built with Thymeleaf for a full demo experience
- Swagger/OpenAPI documentation
- Docker containerization for easier deployment

## Author Sayali — [GitHub](https://github.com/sayali-04)
