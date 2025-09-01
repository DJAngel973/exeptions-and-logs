# Use a base image with OpenJDk 21
FROM openjdk:21-jdk-slim AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the project files (including pom.xml)
COPY . /app

# Build the project using Maven
# The -DskipTests=true option is to skip tests during build.
# The -Dmaven.wagon.http.ssl.insecure=true option is to avoid problems with SSL certificates.
RUN ./mvnw clean package -DskipTests=true -Dmaven.wagon.http.ssl.insecure=true

# --- Execution stage (Runtime Stage) ---
# Use a lighter image for execution to reduce the container size.
FROM openjdk:21-jre-slim

# Copy the generated JAR file from the 'builder' stage to this new image.
COPY --from=builder /app/target/*.jar app.jar

# Exposes port 8080 so the application can be accessed.
EXPOSE 8080

# Command to run the application when the container starts.
ENTRYPOINT ["java", "-jar", "app.jar"]