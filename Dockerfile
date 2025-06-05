# Stage 1: Build the application using Gradle Wrapper
FROM gradle:8.14.0-jdk21 AS build

# Set the working directory in the container
WORKDIR /home/app

# Copy Gradle wrapper and build files
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Copy the source code
COPY src src

# Make gradlew executable
RUN chmod +x gradlew

# Build the application
RUN ./gradlew clean build -x test

# Stage 2: Run the application
FROM openjdk:21-jdk-slim

# Set working directory
WORKDIR /app

# Copy the built jar from the previous stage
COPY --from=build /home/app/build/libs/*.jar app.jar

# Expose port (match your application port, e.g., 8080)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
