

# 🛡️ Zallpy Fraud Detection System

An advanced, enterprise-grade **Fraud Detection & Risk Management System** built with **Spring Boot (Java)**.
This system combines **rule-based detection**, **behavioral analytics**, and **machine learning (Smile)** to identify and prevent fraudulent activities in real time.

---

## 🚀 Overview

The Zallpy Fraud Detection System is designed to simulate a **real fintech-grade fraud engine**, capable of:

* Detecting suspicious transactions and login attempts
* Assigning dynamic risk scores
* Automatically blocking high-risk users
* Providing admin analytics and fraud insights
* Securing APIs with JWT authentication and RBAC

---

## ✨ Features

### 🔐 Authentication & Security

* JWT-based authentication
* Password encryption with BCrypt
* Role-Based Access Control (RBAC)

  * USER
  * ADMIN

### 💳 Transaction Fraud Detection

* Rule-based detection:

  * High-value transactions
  * Foreign location detection
* Velocity checks (multiple transactions in short time)
* Real-time risk scoring

### 🔐 Login Fraud Detection

* Brute-force attack detection
* Suspicious login detection (IP/location/device)
* Login risk scoring

### 🤖 Machine Learning Integration

* Integrated with Smile ML library
* Random Forest model for fraud prediction
* Hybrid detection (Rules + ML)

### 🚨 Fraud Alert System

* Automatic alert generation
* Risk classification (LOW, MEDIUM, HIGH)
* Audit trail for all suspicious activities

### 🚫 Auto User Blocking

* Automatic blocking after repeated high-risk behavior
* Admin-controlled unblocking

### 📊 Dashboard & Analytics

* Total transactions
* Fraud alerts overview
* High-risk activity tracking
* Blocked users monitoring
* Fraud trends over time

---

## 🏗️ Architecture

```
Controller → Service → Risk Engine → ML Engine → Repository → Database
```

### Key Modules:

* **Risk Engine** → Rule-based fraud detection
* **ML Engine** → AI-based fraud prediction
* **Security Layer** → JWT + RBAC
* **Analytics Layer** → Dashboard APIs

---

## 🧱 Tech Stack

| Layer      | Technology            |
| ---------- | --------------------- |
| Backend    | Spring Boot (Java)    |
| Security   | Spring Security + JWT |
| Database   | PostgreSQL            |
| ORM        | Spring Data JPA       |
| ML         | Smile (Random Forest) |
| Build Tool | Maven                 |

---

## ⚙️ Setup & Installation

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/zallpy-fraud-detection.git
cd zallpy-fraud-detection
```

### 2. Configure Database

Update `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/fraud_db
spring.datasource.username=postgres
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
```

### 3. Run the Application

```bash
mvn spring-boot:run
```

---

## 🔑 API Endpoints

### 🔐 Authentication

```
POST /api/auth/login
```

### 👤 User Management

```
POST /api/users/register
```

### 💳 Transactions

```
POST /api/transactions/{userId}
```

### 🔐 Login Events

```
POST /api/login/{userId}
```

### 🚨 Fraud Alerts (ADMIN)

```
GET /api/alerts
```

### 📊 Dashboard (ADMIN)

```
GET /api/dashboard/stats
GET /api/dashboard/trend
```

---

## 🔒 Security

* All endpoints (except auth/register) require JWT token:

```
Authorization: Bearer <token>
```

---

## 🧠 Fraud Detection Logic

### Rule-Based

* High transaction amount
* Foreign location mismatch
* Rapid transaction velocity

### Behavioral

* Multiple failed login attempts
* Suspicious login locations

### Machine Learning

* Random Forest classifier
* Predicts fraud probability
* Enhances rule-based scoring

---

## 🚫 Auto Blocking Logic

A user is automatically blocked if:

* They generate **3 or more HIGH-risk alerts**

Blocked users:

* Cannot login
* Cannot perform transactions

---

## 📈 Future Enhancements

* Real-time streaming (Kafka)
* Email/SMS fraud notifications
* Advanced ML models (XGBoost, Deep Learning)
* Device fingerprinting
* Geo-location risk scoring
* Frontend dashboard (React)

---

## 👨‍💻 Author

**Zallpy AI**
Backend & System Architecture Engineer

---

