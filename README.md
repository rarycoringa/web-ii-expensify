# 💰 Expensify

A personal finance management system built with Spring Boot, featuring JWT authentication and RESTful APIs for managing accounts, transactions, expenses, and income.

## 🚀 Tech Stack

- **Java 25**
- **Spring Boot 4.0.2**
- **Spring Security** with JWT authentication
- **PostgreSQL 13**
- **Docker & Docker Compose**
- **Maven**

## 📋 Prerequisites

- Docker and Docker Compose
- Make (optional, for convenience commands)
- Java 25 (for local development)
- Maven (for local development)

## 🛠️ Getting Started

### 1. Create Docker Network

Before running the application for the first time, create the required Docker network:

```bash
docker network create expensify-network
```

### 2. Start the Application

```bash
make up
```

This command builds and starts both the application and database containers in detached mode.

The application will be available at: **http://localhost:8080**

### 3. View Logs

```bash
make logs
```

Follow the application logs in real-time.

### 4. Stop the Application

```bash
make down
```

Stops and removes all containers.

## 🔑 Authentication

### Create a New User

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"your_username","password":"your_password"}'
```

### Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"your_username","password":"your_password"}'
```

Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Using the JWT Token

Include the token in the Authorization header for protected endpoints:

```bash
curl http://localhost:8080/api/accounts \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## 📚 API Endpoints

### Authentication
- **POST** `/api/auth/register` - Create new user account
- **POST** `/api/auth/login` - Login and receive JWT token

## ⚙️ Configuration

The application uses environment variables for configuration. Default values are set in `docker-compose.yaml`:

| Variable      | Default Value     | Description                   |
|---------------|-------------------|-------------------------------|
| `DB_HOST`     | expensify-db      | Database host                 |
| `DB_PORT`     | 5432              | Database port                 |
| `DB_NAME`     | expensify         | Database name                 |
| `DB_USERNAME` | dummy_user        | Database username             |
| `DB_PASSWORD` | dummy_password    | Database password             |
| `JWT_SECRET`  | (auto-generated)  | JWT signing secret (256-bit)  |

### Generating a New JWT Secret

For production, generate a secure 256-bit secret:

```bash
openssl rand -base64 32
```

Update the `JWT_SECRET` environment variable in `docker-compose.yaml`.

## 🗄️ Database

PostgreSQL 13 is used as the database. Data is persisted in a Docker volume named `expensify-db-data`.

### Access Database Directly

```bash
docker exec -it expensify-db psql -U dummy_user -d expensify
```

## 🔧 Local Development

### Run Without Docker

1. Start PostgreSQL locally or update `application.yaml` with your database configuration
2. Build the project:
   ```bash
   ./mvnw clean install
   ```
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

## 📦 Project Structure

```
src/main/java/br/edu/ufrn/expensify/
├── auth/             # Authentication & JWT
├── config/           # Security & Web configuration
```

## 🐛 Troubleshooting

### Application fails to start with "network not found"
Create the Docker network:
```bash
docker network create expensify-network
```

### JWT WeakKeyException
Ensure your `JWT_SECRET` is at least 256 bits (32 bytes in base64). Generate a new one:
```bash
openssl rand -base64 32
```

### Database connection issues
Check if the database container is running:
```bash
docker ps
```

View database logs:
```bash
docker logs expensify-db
```
