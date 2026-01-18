# Project Name

## 📝 Documentation

Full documentation for the project can be found in:

* [Product Documentation](documentation/product.md)
* [Transaction Documentation](documentation/transaction.md)

## ⚙️ Setup Instructions

This project uses **Docker** and **Docker Compose** for easy setup.

To run the application and all required services, simply run:

```bash
docker-compose up --build
```

This will start the following services:

* Spring Boot application
* MySQL database
* Redis (caching & atomic counter)
* RabbitMQ (message broker)

## 📚 API Documentation

Refer to the Swagger UI or Postman collection for API details:
[Postman Collection](https://documenter.getpostman.com/view/31399628/2sBXVifpYN)

## 🔒 Race Condition Handling

To ensure stock consistency when multiple users perform transactions simultaneously, we implemented:

* **Pessimistic Locking**: Locks the database row during a `SELECT` query to prevent concurrent modifications.
* **RabbitMQ**: Used as a message broker to handle background tasks and asynchronous processing, replacing the role of Celery.

This ensures atomic updates and prevents overselling.

## 🛠️ Tech Stack

* **Java 25** (Amazon Corretto)
* **Spring Boot 3.x**
* **MySQL 8.0** (Persistence)
* **Redis 7** (Caching & Atomic Counter)
* **RabbitMQ** (Message Broker)
* **Docker & Docker Compose** (Containerization)

## 📌 Notes

* Ensure Docker and Docker Compose are installed on your machine.
* After running `docker-compose up --build`, the application should be accessible on the configured port (check `application.properties` or `application.yml`).
* All documentation and API references are linked above.
