# Use OpenJDK 8 Alpine image
FROM openjdk:8-jdk-alpine

# Set working directory inside container
WORKDIR /app

# Copy the built JAR file into container
COPY target/iso8583-simulator-1.0.0.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the JAR
ENTRYPOINT ["java","-jar","/app/app.jar"]
