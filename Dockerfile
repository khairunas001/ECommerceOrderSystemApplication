# Stage 1: Build menggunakan JDK 25 dan install Maven manual
FROM amazoncorretto:25 AS build
WORKDIR /app

# Install Maven
RUN yum install -y maven

# Copy pom.xml dan download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code dan build jar
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM amazoncorretto:25-alpine
WORKDIR /app

# Copy jar dari stage build
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]