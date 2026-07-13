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
- **Frontend:** Thymeleaf, HTML/CSS, vanilla JavaScript (Fetch API/AJAX)
- **Database:** MySQL, Hibernate ORM
- **API Documentation:** Swagger / OpenAPI (springdoc-openapi)
- **Build Tool:** Maven
- **Testing:** Postman
- **Version Control:** Git, GitHub

## Key Features

### 1. Batch-Wise Inventory Tracking
Every stock delivery is tracked as its own batch with its own expiry date, instead of one blended quantity per product.

### 2. FIFO Stock Deduction
When stock is used or sold, the system automatically deducts from the batch expiring soonest first — a small algorithmic feature that mirrors real warehouse practice.

### 3. Automated Expiry Alerts
A scheduled background job (`@Scheduled`) checks daily for batches nearing expiry and raises alerts automatically, with a manual trigger endpoint for on-demand checks. Duplicate alerts for the same batch are prevented by checking existing unresolved alerts before creating new ones.

### 4. Role-Based Access Control
Spring Security with BCrypt password hashing distinguishes Admin (full access) from Staff (restricted access) — for example, only Admins can add new stock batches.

### 5. Web Interface
A lightweight multi-page frontend built with Thymeleaf, covering the core workflows end-to-end:
- **Products page** — view and add products via AJAX, no page reloads
- **Stock Batches page** — add stock, deduct stock (FIFO), and view active batches per product
- **Alerts page** — view active alerts and manually trigger the expiry check

### 6. Interactive API Documentation
Full Swagger/OpenAPI UI at `/swagger-ui/index.html`, letting anyone explore and test every endpoint directly from the browser.

## Architecture
Client (Browser / Postman)
↓ REST APIs / Thymeleaf views
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

## Web Pages

| Page | URL | Description |
|------|-----|-------------|
| Products | `/products-page` | View and add products |
| Stock Batches | `/stock-page` | Add/deduct stock, view active batches |
| Alerts | `/alerts-page` | View and trigger expiry alerts |
| API Docs | `/swagger-ui/index.html` | Interactive API documentation |

## Setup Instructions

1. Clone the repository:git clone https://github.com/sayali-04/smartstock.git
2. Create a MySQL database:
```sql CREATE DATABASE smartstock_db;
```
3. Update `src/main/resources/application.properties` with your MySQL username/password
4. Run the application:mvn spring-boot:run
5. Default admin login: `username: admin`, `password: admin123`
6. Visit `http://localhost:8080/products-page` to use the app, or `http://localhost:8080/swagger-ui/index.html` for API docs

## Future Improvements
- JWT-based authentication instead of HTTP Basic Auth
- Proper login page instead of hardcoded credentials in frontend JS
- Docker containerization for easier deployment
- Low-stock alerts based on reorder threshold (currently only expiry alerts are implemented)

## Author
Sayali — [GitHub](https://github.com/sayali-04)