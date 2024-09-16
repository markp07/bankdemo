# BankDemo

## Overview
BankDemo is a Spring Boot application designed to simulate basic banking operations. It uses Java for the backend logic, SQL for database interactions, and Maven for project management.

## Features
- User account management
- Transaction processing
- Balance inquiries
- Transaction history

## Prerequisites
- Java 11 or higher
- Maven 3.6.0 or higher
- Docker and Docker Compose

## Setup

### Clone the repository
```sh
git clone https://github.com/markp07/bankdemo.git
cd bankdemo
```

### Configure the database
Update the `application.properties` file located in `src/main/resources` with your database configuration:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bankdemo
spring.datasource.username=yourusername
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

### Build the project
```sh
mvn clean install
```

### Run the application
```sh
mvn spring-boot:run
```

## Docker Setup

### Build Docker Image
```sh
docker build -t bankdemo:latest .
```

### Run with Docker Compose
```sh
docker compose up
```

## Usage
Once the application is running, you can access it at `http://localhost:8080`. Use the provided endpoints to interact with the banking system.

## Endpoints
- `GET /accounts` - List all accounts
- `POST /accounts` - Create a new account
- `GET /accounts/{id}` - Get account details
- `PUT /accounts/{id}` - Update account information
- `DELETE /accounts/{id}` - Delete an account
- `POST /transactions` - Process a transaction
- `GET /transactions` - List all transactions

## Shell Scripts
### Start the application
```sh
./scripts/start.sh
```

### Stop the application
```sh
./scripts/stop.sh
```

## Contributing
1. Fork the repository
2. Create a new branch (`git checkout -b feature-branch`)
3. Commit your changes (`git commit -am 'Add new feature'`)
4. Push to the branch (`git push origin feature-branch`)
5. Create a new Pull Request

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more information.

## Contact
For any inquiries, please contact [markp07](https://github.com/markp07).