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

### Register

```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"your_username","password":"your_password"}'
```

### Login

```bash
curl -X POST http://localhost:8080/auth/login \
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
curl http://localhost:8080/accounts \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## 📚 API Endpoints

### Authentication
- **POST** `/auth/register` - Register new user
  - Body: `{"username": "string", "password": "string"}`
  - Returns: `{"id": "uuid", "username": "string"}`

- **POST** `/auth/login` - Login and receive JWT token
  - Body: `{"username": "string", "password": "string"}`
  - Returns: `{"token": "jwt_token"}`

### Accounts
- **GET** `/accounts` - Get all accounts for authenticated user
  - Returns: Array of accounts with `{id, name, balance}`

- **GET** `/accounts/{id}` - Get account by ID
  - Returns: `{id, name, balance}`

- **POST** `/accounts` - Create new account
  - Body: `{"name": "string", "balance": "number"}`
  - Returns: `{id, name, balance}`

- **PATCH** `/accounts/{id}` - Update account name
  - Body: `{"name": "string"}`
  - Returns: `{id, name, balance}`

- **DELETE** `/accounts/{id}` - Delete account
  - Returns: 204 No Content

### Transactions - Incomes
- **GET** `/transactions/incomes` - Get all incomes for authenticated user
  - Returns: Array of incomes with `{id, description, amount, date, account_id}`

- **GET** `/transactions/incomes/{id}` - Get income by ID
  - Returns: `{id, description, amount, date, account_id}`

- **POST** `/transactions/incomes` - Create new income
  - Body: `{"description": "string", "amount": "number", "date": "string", "account_id": "uuid"}`
  - Returns: `{id, description, amount, date, accountId}`

- **DELETE** `/transactions/incomes/{id}` - Delete income
  - Returns: 204 No Content

### Transactions - Expenses
- **GET** `/transactions/expenses` - Get all expenses for authenticated user
  - Returns: Array of expenses with `{id, description, amount, date, account_id}`

- **GET** `/transactions/expenses/{id}` - Get expense by ID
  - Returns: `{id, description, amount, date, account_id}`

- **POST** `/transactions/expenses` - Create new expense
  - Body: `{"description": "string", "amount": "number", "date": "string", "account_id": "uuid"}`
  - Returns: `{id, description, amount, date, account_id}`

- **DELETE** `/transactions/expenses/{id}` - Delete expense
  - Returns: 204 No Content

### Transactions - Transfers
- **GET** `/transactions/transfers` - Get all transfers for authenticated user
  - Returns: Array of transfers with `{id, description, amount, date, source_account_id, destination_account_id}`

- **GET** `/transactions/transfers/{id}` - Get transfer by ID
  - Returns: `{id, description, amount, date, source_account_id, destination_account_id}`

- **POST** `/transactions/transfers` - Create new transfer
  - Body: `{"description": "string", "amount": "number", "date": "string", "source_account_id": "uuid", "destination_account_id": "uuid"}`
  - Returns: `{id, description, amount, date, source_account_id, destination_account_id}`

- **DELETE** `/transactions/transfers/{id}` - Delete transfer
  - Returns: 204 No Content

**Note:** All endpoints except `/auth/register` and `/auth/login` require JWT authentication. Include the token in the Authorization header: `Authorization: Bearer YOUR_JWT_TOKEN`

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
