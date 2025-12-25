# 🚀 Inventory Management System with Web Interface

A complete Spring Boot application with **modern web interface**, RESTful API, and Swagger documentation for managing products and transactions.

---

## 🌟 **Features Overview**

### 📱 **Web Interface (NEW!)**
- **Modern Dashboard** with real-time statistics and charts
- **Product Management** with full CRUD operations
- **Transaction Management** with sales & purchase tracking
- **Responsive Design** for desktop, tablet, and mobile
- **Interactive Charts** using Chart.js
- **Secure Authentication** with login page

### 🔌 **RESTful API**
- **Product Management**: Full CRUD operations
- **Transaction Management**: Sales and purchases with stock tracking
- **Search & Filter**: Advanced filtering capabilities
- **Statistics & Analytics**: Comprehensive reporting
- **Swagger Documentation**: Interactive API documentation

### 🛡️ **Security & Database**
- **Spring Security**: Secure authentication system
- **H2 Database**: In-memory database for development
- **JPA/Hibernate**: ORM integration
- **Data Validation**: Input validation and error handling

---

## 🏗️ **Architecture Overview**

```
┌─────────────────────────────────────────────────────────────┐
│                    📱 Web Interface                 │
├─────────────────────────────────────────────────────────────┤
│  🎨 Frontend          │          📊 Backend           │
│                       │                               │
│  • Bootstrap 5       │  • Spring Boot 4.0.1       │
│  • Chart.js          │  • Spring MVC              │
│  • Thymeleaf        │  • Spring Security           │
│  • Custom CSS        │  • Spring Data JPA          │
│  • Responsive Design │  • H2 Database              │
└─────────────────────────────────────────────────────────────┘
```

---

## 📁 **Project Structure**

```
src/main/
├── java/com/example/demo/
│   ├── 📋 controller/
│   │   ├── WebController.java           # Web page routing
│   │   ├── ProductController.java        # REST API
│   │   └── TransactionController.java   # REST API
│   ├── 🗂️ model/
│   │   ├── Product.java                # Product entity
│   │   └── Transaction.java           # Transaction entity
│   ├── 🔧 repository/
│   │   ├── ProductRepository.java        # Data access
│   │   └── TransactionRepository.java   # Data access
│   ├── 💼 service/
│   │   ├── ProductService.java         # Business logic
│   │   └── TransactionService.java      # Business logic
│   └── ⚙️ config/
│       ├── SecurityConfig.java           # Security config
│       └── SwaggerConfig.java           # API docs config
├── resources/
│   ├── 📄 templates/                 # Web pages
│   │   ├── layout.html                # Base template
│   │   ├── login.html                 # Login page
│   │   ├── dashboard.html             # Dashboard
│   │   ├── products/
│   │   │   └── list.html            # Product management
│   │   └── transactions/
│   │       └── list.html            # Transaction management
│   └── 🎨 static/                    # Static resources
│       ├── css/style.css               # Custom styling
│       └── js/app.js                  # JavaScript logic
└── application.properties             # Configuration
```

---

## 🚀 **Getting Started**

### 📋 **Prerequisites**
- **Java 17+** - Java Development Kit
- **Maven 3.6+** - Build tool
- **Modern Browser** - Chrome, Firefox, Safari, Edge

### 🏃‍♂️ **Quick Start**

1. **Clone/Download the project**
2. **Build and run**:
```bash
mvn clean spring-boot:run
```

3. **Access the application**:
- **Web Interface**: `http://localhost:8080/login`
- **API Documentation**: `http://localhost:8080/swagger-ui.html`
- **H2 Database Console**: `http://localhost:8080/h2-console`

---

## 🔐 **Authentication**

### **Login Credentials**
- **Username**: `admin`
- **Password**: `admin123`

### **Access Points**
- **🌐 Web Login**: `http://localhost:8080/login`
- **📊 Dashboard**: `http://localhost:8080/dashboard` (requires login)
- **📦 Products**: `http://localhost:8080/products` (requires login)
- **💰 Transactions**: `http://localhost:8080/transactions` (requires login)
- **📚 API Docs**: `http://localhost:8080/swagger-ui.html` (public)
- **🗄️ Database**: `http://localhost:8080/h2-console` (public)

---

## 🌐 **Web Interface Features**

### 📊 **Dashboard**
- **Real-time Statistics**: Total products, stock levels, transaction counts
- **Interactive Charts**: Transaction trends and revenue analytics
- **Quick Actions**: Fast access to common operations
- **Recent Activity**: Latest transactions and low stock alerts

### 📦 **Product Management**
- **Full CRUD**: Create, Read, Update, Delete products
- **Advanced Search**: Filter by name, price range, stock status
- **Bulk Operations**: Select and manage multiple products
- **Data Export**: Export to CSV format
- **Pagination**: Efficient data handling for large datasets

### 💰 **Transaction Management**
- **Quick Transactions**: Fast sales and purchase creation
- **Transaction History**: Complete audit trail with filters
- **Analytics Dashboard**: Sales vs purchases visualization
- **Auto Stock Updates**: Real-time inventory updates

### 📱 **Responsive Design**
- **Mobile Optimized**: Touch-friendly interface
- **Adaptive Layout**: Works on all screen sizes
- **Hamburger Menu**: Collapsible navigation for mobile
- **Optimized Tables**: Horizontal scroll on mobile

---

## 🔌 **RESTful API**

### 📦 **Product Endpoints**
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/products` | Get all products |
| GET | `/api/products/{id}` | Get product by ID |
| POST | `/api/products` | Create new product |
| PUT | `/api/products/{id}` | Update product |
| DELETE | `/api/products/{id}` | Delete product |
| GET | `/api/products/search?name={name}` | Search products |
| GET | `/api/products/price-range?min={min}&max={max}` | Filter by price |
| GET | `/api/products/in-stock` | Get available products |
| GET | `/api/products/low-stock/{threshold}` | Get low stock products |

### 💰 **Transaction Endpoints**
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/transactions` | Get all transactions |
| POST | `/api/transactions/sale?productId={id}&quantity={qty}&description={desc}` | Create sale |
| POST | `/api/transactions/purchase?productId={id}&quantity={qty}&description={desc}` | Create purchase |
| GET | `/api/transactions/stats/total-sales` | Get total sales |
| GET | `/api/transactions/stats/net-revenue` | Get net revenue |

### 📊 **Statistics Endpoints**
- **Total Sales**: Sum of all sale transactions
- **Total Purchases**: Sum of all purchase transactions
- **Net Revenue**: Sales minus purchases
- **Transaction Counts**: By type and date range

---

## 🎨 **Frontend Technologies**

### **🎯 Core Stack**
- **Bootstrap 5**: Modern responsive UI framework
- **Thymeleaf**: Server-side template engine
- **Chart.js**: Interactive data visualization
- **JavaScript ES6+**: Modern browser scripting
- **CSS3**: Advanced styling with animations

### **✨ Design Features**
- **Gradient Effects**: Modern color transitions
- **Hover Animations**: Interactive feedback
- **Responsive Grid**: Mobile-first approach
- **Dark Mode Ready**: CSS custom properties
- **Print Optimized**: Clean print styles

---

## 🔧 **Configuration**

### **Application Properties** (`application.properties`)
```properties
# Server Configuration
server.port=8080

# Database (H2)
spring.datasource.url=jdbc:h2:mem:demo_db
spring.datasource.driver-class-name=org.h2.Driver
spring.h2.console.enabled=true

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

# Security
spring.security.user.name=admin
spring.security.user.password=admin123

# Swagger/OpenAPI
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
```

### **Security Configuration** (`SecurityConfig.java`)
- **Form Login**: Custom login page
- **API Protection**: Basic auth for REST endpoints
- **Public Access**: Login, static resources, Swagger UI
- **CORS Enabled**: Cross-origin requests support

---

## 💾 **Database Schema**

### **Products Table**
```sql
CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price INTEGER NOT NULL,
    stock INTEGER NOT NULL
);
```

### **Transactions Table**
```sql
CREATE TABLE transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    quantity INTEGER NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    transaction_type VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    description VARCHAR(255),
    FOREIGN KEY (product_id) REFERENCES products(id)
);
```

---

## 🚀 **Running the Application**

### **Start the Application**
```bash
# Using Maven
mvn clean spring-boot:run

# Or using Maven Wrapper
./mvnw spring-boot:run
```

### **Access Points**
1. **🌐 Open Browser**: `http://localhost:8080`
2. **🔐 Login**: `admin` / `admin123`
3. **📊 Explore Dashboard**: View statistics and charts
4. **📦 Manage Products**: Add/edit/remove products
5. **💰 Handle Transactions**: Create sales and purchases
6. **📚 Test API**: Use Swagger UI at `/swagger-ui.html`

---

## 🧪 **Testing**

### **API Testing with cURL**
```bash
# Create a product
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -u admin:admin123 \
  -d '{"name":"Laptop Gaming","price":15000000,"stock":25}'

# Create a sale
curl -X POST "http://localhost:8080/api/transactions/sale?productId=1&quantity=2&description=Sold to customer" \
  -u admin:admin123

# Get all products
curl -X GET http://localhost:8080/api/products -u admin:admin123
```

### **Web Interface Testing**
- **Login functionality**: Test authentication
- **CRUD Operations**: Create, read, update, delete
- **Search & Filter**: Test data filtering
- **Responsive Design**: Test on different screen sizes
- **API Integration**: Test web-to-backend connectivity

---

## 🐛 **Troubleshooting**

### **Common Issues**
1. **Port Already in Use**: Change `server.port` in `application.properties`
2. **Database Issues**: Check H2 console at `/h2-console`
3. **Authentication Problems**: Verify credentials and security config
4. **Static Resources Not Loading**: Check file paths and permissions

### **Logs and Debugging**
- **Application Logs**: Console output shows startup status
- **SQL Debugging**: `spring.jpa.show-sql=true` shows database queries
- **Browser Console**: Check JavaScript errors in developer tools

---

## 🚀 **Deployment**

### **Development**
```bash
# Run with H2 (default)
mvn spring-boot:run
```

### **Production**
```bash
# Build JAR
mvn clean package

# Run JAR
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

### **Production Considerations**
- **External Database**: Configure MySQL/PostgreSQL
- **Environment Variables**: Use environment-specific config
- **Reverse Proxy**: Configure Nginx/Apache
- **HTTPS**: SSL certificate configuration
- **Monitoring**: Add logging and metrics

---

## 🎯 **Future Enhancements**

### **📱 Mobile App**
- **Progressive Web App**: Offline capabilities
- **Mobile-First Design**: Touch-optimized interface
- **Push Notifications**: Stock alerts and updates

### **🔧 Advanced Features**
- **User Management**: Multiple users with roles
- **Advanced Reporting**: PDF exports, scheduled reports
- **Inventory Forecasting**: Predictive analytics
- **Integration APIs**: Payment gateways, shipping

### **📊 Business Intelligence**
- **Real-time Dashboard**: WebSocket updates
- **Advanced Analytics**: Sales trends, profitability analysis
- **Integration**: ERP systems, accounting software

---

## 🤝 **Contributing**

### **Development Setup**
1. **Fork the repository**
2. **Create feature branch**: `git checkout -b feature/new-feature`
3. **Make changes** and test thoroughly
4. **Commit changes**: `git commit -am "Add new feature"`
5. **Push changes**: `git push origin feature/new-feature`
6. **Create Pull Request**

### **Code Style**
- **Java**: Follow Google Java Style Guide
- **JavaScript**: Use ES6+ features, consistent formatting
- **CSS**: BEM methodology, mobile-first approach
- **Documentation**: Comprehensive comments and README

---

## 📄 **License**

This project is open source and available under the **MIT License**.

---

## 🎉 **Enjoy!**

Your complete inventory management system is ready to use! 🚀

- **🌐 Web Interface**: Modern, responsive, user-friendly
- **🔌 REST API**: Full-featured, well-documented
- **🛡️ Secure**: Authentication and authorization
- **📊 Analytics**: Real-time insights and reporting

Start managing your inventory efficiently with this comprehensive solution! 🎯