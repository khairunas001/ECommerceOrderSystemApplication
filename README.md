# Project Name

## 📝 Documentation

Full documentation for the project can be found in:

* [Product Documentation](documentation/product.md)
* [Transaction Documentation](documentation/transaction.md)

## ⚙️ Setup Instructions

This project uses **Docker** and **Docker Compose** for easy setup.

> **Note:** Make sure you are running commands from the root folder of the project:  
> `/ECommerceOrderSystemApplication`

### 🏠 Build JAR Locally (Before Docker)

Before running the application with Docker, you can build the JAR locally:

1. Make sure you are in the root folder of the project (where `pom.xml` is located).

2. Using the bundled Maven wrapper:

```powershell
./mvnw clean package -DskipTests
```

3. Or if you have Maven installed on Windows:

```powershell
mvn clean package -DskipTests
```

4. After building, the JAR file will be available in the `target/` directory.

### 🐳 Run with Docker Compose

Once the JAR is built, you can start the application and all required services with:

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


## 🔌 Accessing Services

After running `docker-compose up --build`, you can access the services as follows:

### 🐬 MySQL

- **Host:** `localhost`
- **Port:** `3306`
- **Database:** `e_commerce_order_system`
- **Username:** `ecommerce`
- **Password:** `ecommerce123`

You can connect using the MySQL client:

```bash
mysql -h 127.0.0.1 -P 3306 -u ecommerce -pecommerce123 e_commerce_order_system
```

Or using any database GUI tool (e.g., DBeaver, MySQL Workbench) with the above credentials.

You can also check directly inside the MySQL Docker container:

```bash
docker exec -it ecommerce-mysql mysql -u ecommerce -pecommerce123 e_commerce_order_system
```

From here, you can run SQL queries to verify data has been stored correctly.

---

### 🐇 RabbitMQ

- **Host:** `localhost`
- **Port (AMQP):** `5672`
- **Port (Management UI):** `15672`
- **Username:** `guest`
- **Password:** `guest`

You can access the RabbitMQ Management UI in your browser:

```
http://localhost:15672
```

Use the credentials above to login.

If the background worker is running, you should see messages like:

```
📥 Transaction received: a241075b-d542-4416-aa24-d9a155ce8b51
⚙️ Transaction processed: a241075b-d542-4416-aa24-d9a155ce8b51
✅ Transaction success:  a241075b-d542-4416-aa24-d9a155ce8b51
```

These logs indicate that transactions are being processed successfully.

---

### 🟢 Redis

- **Host:** `localhost`
- **Port:** `6379`

You can connect using the Redis CLI:

```bash
redis-cli -h 127.0.0.1 -p 6379
```

Or use any Redis GUI tool (e.g., RedisInsight) with the above host and port.

When queries hit the cache successfully, you will see logs like:

```
🔥 CACHE HIT products:all
```

To check directly inside the Redis Docker container:

```bash
docker exec -it ecommerce-redis redis-cli
```

Then you can run Redis commands, for example:

```redis
KEYS *
GET products:all
```

This allows you to verify that data is stored in Redis.

---

✅ All services (MySQL, RabbitMQ, Redis) should now be running and accessible, and you can verify that transactions are processed and cached correctly.

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
