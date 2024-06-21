# Use official OpenJDK runtime as a parent image
FROM openjdk:17-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged Spring Boot application JAR file into the container at /app
COPY target/practiceProject-0.0.1-SNAPSHOT.jar /app/practiceProject-0.0.1-SNAPSHOT.jar

# Expose the port that the Spring Boot application will run on
EXPOSE 8085

# Specify the command to run your Spring Boot application using java -jar
CMD ["java", "-jar", "practiceProject-0.0.1-SNAPSHOT.jar"]