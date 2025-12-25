# Simple Inventory Management API

A Spring Boot application with Swagger/OpenAPI documentation for managing products and transactions.

## Features

- **Product Management**: Full CRUD operations for inventory management
- **Transaction Management**: Sales and purchase transactions with stock tracking
- **Swagger Documentation**: Interactive API documentation at `/swagger-ui.html`
- **Spring Security**: Basic authentication for API endpoints
- **MySQL Database**: JPA/Hibernate integration

## Getting Started

### Prerequisites
- Java 17+
- Maven 3.6+
- MySQL Server

### Database Setup

1. Create MySQL database:
```sql
CREATE DATABASE demo_db;
```

2. Update database credentials in `src/main/resources/application.properties` if needed.

### Running the Application

1. Build and run:
```bash
mvn clean spring-boot:run
```

2. The application will start on `http://localhost:8080`

### Accessing Swagger UI

- URL: `http://localhost:8080/swagger-ui.html`
- Authentication: Username: `admin`, Password: `admin123`

### API Endpoints

#### Product Management
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `POST /api/products` - Create new product
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product
- `GET /api/products/search?name={name}` - Search products by name
- `GET /api/products/price-range?minPrice={min}&maxPrice={max}` - Get products by price range
- `GET /api/products/in-stock` - Get in-stock products
- `GET /api/products/low-stock/{threshold}` - Get low stock products
- `GET /api/products/out-of-stock` - Get out of stock products

#### Transaction Management
- `GET /api/transactions` - Get all transactions
- `GET /api/transactions/{id}` - Get transaction by ID
- `POST /api/transactions` - Create transaction
- `POST /api/transactions/sale` - Create sale
- `POST /api/transactions/purchase` - Create purchase
- `PUT /api/transactions/{id}` - Update transaction
- `DELETE /api/transactions/{id}` - Delete transaction
- `GET /api/transactions/product/{productId}` - Get transactions by product
- `GET /api/transactions/type/{transactionType}` - Get transactions by type
- `GET /api/transactions/date-range?startDate={start}&endDate={end}` - Get transactions by date range

#### Transaction Statistics
- `GET /api/transactions/stats/total-sales` - Get total sales amount
- `GET /api/transactions/stats/total-purchases` - Get total purchases amount
- `GET /api/transactions/stats/net-revenue` - Get net revenue

### Example Usage

1. Create a product:
```bash
curl -u admin:admin123 -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{"name":"Laptop","price":1000,"stock":50}'
```

2. Create a sale:
```bash
curl -u admin:admin123 -X POST "http://localhost:8080/api/transactions/sale?productId=1&quantity=5&description=Sold to customer"
```

3. Check Swagger UI for more examples and test the API interactively.

## Security

The application uses Spring Security with basic authentication:
- Username: `admin`
- Password: `admin123`

Swagger UI endpoints are publicly accessible, but API endpoints require authentication.

## Database Schema

The application creates the following tables:
- `products` - Product information (id, name, price, stock)
- `transactions` - Transaction records (id, product_id, quantity, total_price, transaction_type, created_at, description)