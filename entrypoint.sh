# ===== Stage 1: Build =====
FROM maven:3.9.2-eclipse-temurin-20 AS build
WORKDIR /app

# Copy project
COPY pom.xml .
COPY src ./src

# Build jar
RUN mvn clean package -DskipTests

# ===== Stage 2: Run =====
FROM amazoncorretto:25
WORKDIR /app

# Copy jar dari stage build
COPY --from=build /app/target/*.jar app.jar

# Copy wait-for-it.sh dan beri executable permission
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

# Expose port Spring Boot
EXPOSE 8080

# Entry point: tunggu MySQL siap lalu jalankan jar
ENTRYPOINT ["/wait-for-it.sh", "ecommerce-mysql:3306", "--timeout=60", "--strict", "--", "java", "-jar", "/app/app.jar"]

